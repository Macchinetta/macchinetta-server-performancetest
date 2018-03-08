#!/bin/sh

##############################################################################################
# JMS用性能試験自動化スクリプト (Tomcat用)
#
# サーバの起動停止、デプロイ、getlogsの起動、ログの退避など、
# 性能試験で必要な一連の作業を自動化します。
##############################################################################################



#==============================================================================
# 多重負荷試験設定
#==============================================================================

if [ ${#} -eq 15 ]; then

    echo "指定された引数は15個です。"
    echo "第1引数:実施中MQデータ登録有無=${1}"
    echo "第2引数:性能試験実施回数=${2}"
    echo "第3引数:試験実施前メッセージ取得時間設定（秒）=${3}"
    echo "第4引数:測定時間設定（秒）=${4}"
    echo "第5引数:性能試験実施Message Listenerのスレッド数パターン=${5}"
    echo "第6引数:ログ記録間隔秒数=${6}"
    echo "第7引数:ログ出力ディレクトリ（branch名）=${7}"
    echo "第8引数:AP_SSH_PORT=${8}"
    echo "第9引数:AP_WEB_PORT=${9}"
    echo "第10引数:AP_IP=${10}"
    echo "第11引数:DB_SSH_PORT=${11}"
    echo "第12引数:DB_IP=${12}"
    echo "第13引数:MQ_SSH_PORT=${13}"
    echo "第14引数:MQ_MQ_PORT=${14}"
    echo "第15引数:MQ_IP=${15}"
    echo "を変更します。"

    #性能試験タイプ
    ONGOING_REGISTER_DATA=${1}

    #性能試験実施回数
    RUNTIMES=${2}

    #試験実施前メッセージ取得時間設定（秒）
    SAVE_DURATION=${3}

    #測定時間設定（秒）
    JMETER_DURATION=${4}

    #性能試験実施Message Listenerのスレッド数パターン
    CONCURRENCIES=(${5//,/ })

    #システムログ記録パラメータ
    #ログ記録間隔秒数
    LOG_INTEVAL_SEC=${6}

    #ログ出力ディレクトリ
    VARSION_DIR=${7}

    #ログ記録回数
    LOG_COUNT=`expr ${JMETER_DURATION} / ${LOG_INTEVAL_SEC} + 1`

    AP_SSH_PORT=${8}
    AP_WEB_PORT=${9}
    AP_IP=${10}
    DB_SSH_PORT=${11}
    DB_IP=${12}
    MQ_SSH_PORT=${13}
    MQ_MQ_PORT=${14}
    MQ_IP=${15}

else

    echo "指定された引数は15個以外です。"
    echo "実行できないので、中止します。"

    exit 1;

fi

#    #性能試験実施回数
#    RUNTIMES=3
#
#    #測定時間設定（秒） 20分
#    JMETER_DURATION=1200
#
#    #性能試験実施Message Listenerのスレッド数パターン
#    CONCURRENCIES=("1" "10" "20" "30" "40" "50" "60" "70" "80" "90" "100")
#
#    #ログ出力ディレクトリ（branch名）
#    VARSION_DIR=${1}
#
#    #システムログ記録パラメータ
#    #ログ記録間隔秒数
#    LOG_INTEVAL_SEC=10
#
#    #ログ記録回数
#    LOG_COUNT=121


#==============================================================================
# 長安試験設定
#==============================================================================

#性能試験実施Message Listenerのスレッド数パターン 200スレッド
#CONCURRENCIES=("50")

#性能試験実施回数 1回
#RUNTIMES=1

#測定時間設定（秒） 48時間
#JMETER_DURATION=172800

#システムログ記録パラメータ
#ログ記録間隔秒数
#LOG_INTEVAL_SEC=60

#ログ記録回数
#LOG_COUNT=2881


#==============================================================================
# 共通設定
#==============================================================================

#デプロイwarファイル名
TARGETS=("atrs.war")

#ATRSインストールホームフォルダ
ATRS_HOME=/opt/atrs

#シェル格納フォルダ
ATRS_SHELL_DIR=${ATRS_HOME}/shell

#ログ記録フォルダー
ATRS_LOG_HOME=/var/log/atrs

#getlogsログフォルダ
GETLOGS_DIR=${ATRS_LOG_HOME}/getlogs/${VARSION_DIR}

#root管理ユーザ
ROOT_USER=root

#==============================================================================
# 負荷ツールサーバ設定
#==============================================================================

#JMeterインストール先
JMETER_HOME=/opt/jmeter/jmeter

#シナリオ格納フォルダ
ATRS_SCENARIO_DIR=${ATRS_HOME}/scenario

#==============================================================================
# APサーバ設定
#==============================================================================

#tomcat service管理ユーザ
TOMCAT_USER=tomcat

#TOMCATサーバインストール先
TOMCAT_HOME=/opt/tomcat/tomcat

#TOMCATサーバアドレス
TOMCAT_IP=${AP_IP}

#WEBサーバPort
TOMCAT_WEB_PORT=${AP_WEB_PORT}

#SSHサーバPort
AP_SSH_PORT=${AP_SSH_PORT}

#atrsデプロイ資材格納フォルダ
ATRS_DEPLOY_DIR=${ATRS_HOME}/deploy/tomcat


#==============================================================================
# DBサーバ設定
#==============================================================================

#DBサーバログインユーザー
POSTGRES_USER=postgres

#PostgreSQLサーバアドレス
POSTGRES_IP=${DB_IP}

#SSHサーバPort
DB_SSH_PORT=${DB_SSH_PORT}

#テストデータ作成SQL格納フォルダー
TEST_DATA_HOME=${ATRS_HOME}/testdata

#==============================================================================
# MQサーバ設定
#==============================================================================

#ActiveMQ service管理ユーザ
ACTIVEMQ_USER=activemq

#ActiveMQサーバインストール先
ACTIVEMQ_HOME=/opt/activemq/activemq

#ActiveMQサーバアドレス
ACTIVEMQ_IP=${MQ_IP}

#SSHサーバPort
MQ_SSH_PORT=${MQ_SSH_PORT}

#MQサーバPort
ACTIVEMQ_MQ_PORT=${MQ_MQ_PORT}

#==============================================================================
# 手順1 ATRS業務DBの 初期設定
#==============================================================================
initdb() {
    echo "[1] initialize table"

    ssh ${POSTGRES_IP} -l ${POSTGRES_USER} -p ${DB_SSH_PORT} "${ATRS_SHELL_DIR}/generateTestData.sh >/dev/null 2>&1"
    echo "    done."
}


#==============================================================================
# 手順2 ActiveMQの起動
#   引数なし
#==============================================================================
startActiveMQ() {
    echo "[2] start ActiveMQ"
    ssh ${ACTIVEMQ_IP} -l ${ROOT_USER} -p ${MQ_SSH_PORT} "rm -rf /opt/activemq/activemq/data/*"
    if [ ${SAVE_DURATION} -ne "0" ]; then
    ssh ${ACTIVEMQ_IP} -l ${ACTIVEMQ_USER} -p ${MQ_SSH_PORT} "cp -r /opt/atrs/kahadb /opt/activemq/activemq/data"
    fi
    ssh ${ACTIVEMQ_IP} -l ${ROOT_USER} -p ${MQ_SSH_PORT} "rm -f ${ATRS_LOG_HOME}/activemq/activemq.log"
    sleep 30
    ssh ${ACTIVEMQ_IP} -l ${ROOT_USER} -p ${MQ_SSH_PORT} "systemctl start activemq"

    echo "    done."

}

#==============================================================================
# 手順3 アプリケーションのデプロイ
#   引数1 : デプロイ対象のアーカイブ名 (e.g. atrs),拡張子は不要
#==============================================================================
deploy() {
    echo "[3] deploy application"

    local appName=$1
    echo "    deploy ${appName}.war"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "cp ${ATRS_DEPLOY_DIR}/${appName}.war ${TOMCAT_HOME}/webapps/"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "cp ${ATRS_DEPLOY_DIR}/${appName}.xml ${TOMCAT_HOME}/conf/Catalina/localhost/"
    echo "    done."
}

#==============================================================================
# 手順4 Tomcatの起動
#   引数1 : デプロイ対象のアーカイブ名 (e.g. atrs),拡張子は不要
#==============================================================================
startTomcat() {
    echo "[4] start Tomcat"

    local appName=$1
    local concurrency=$2

    # Tomcat Message Listenerのスレッド数 変更
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "cp -p ${TOMCAT_HOME}/bin/setenv.sh.org ${TOMCAT_HOME}/bin/setenv.sh"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "sed -i -e \"s/atrs.jms.concurrency=1/atrs.jms.concurrency=${concurrency}/\" ${TOMCAT_HOME}/bin/setenv.sh"

    # Tomcat サービス起動
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "systemctl start tomcat"

    echo -n "    wait 30sec"
    for i in $(seq 1 6)
    do
      sleep 5
      echo -n .
    done

    echo

    # wgetでトップページを取得することでデプロイ正常性確認
    WAIT_TIME=10
    MAX_COUNT=5
    NOW_COUNT=0
    RET=1

    while [ "${RET}" -ne "0" -a "${NOW_COUNT}" -lt "${MAX_COUNT}" ]
    do

        sleep ${WAIT_TIME}

        wget http://${TOMCAT_IP}:${TOMCAT_WEB_PORT}/${appName}/ >/dev/null 2>&1

        RET=$?

        if [ "${RET}" -ne "0" ]; then
            echo "Tomcatがまだ正常起動していません。"
            echo "リトライ回数：${NOW_COUNT}回目、sleep秒数=${WAIT_TIME}"
        else
            echo "wget http://${TOMCAT_IP}:${TOMCAT_WEB_PORT}/${appName}/ >/dev/null 2>&1"
            echo "Tomcatが正常起動しました。"
            RET=0
        fi

        NOW_COUNT=`expr ${NOW_COUNT} + 1`

    done

    if [ ${RET} -ne "0" -o "${NOW_COUNT}" -eq "${MAX_COUNT}" ]; then
        echo "Tomcatが正常起動していません。"
        echo "wget http://${TOMCAT_IP}:${TOMCAT_WEB_PORT}/${appName}/ >/dev/null 2>&1"
        echo "戻り値=${RET}"
        echo "リトライ回数=${NOW_COUNT}回目、最大リトライ回数=${MAX_COUNT}"
        exit 1
    fi

    echo "    done."

}

#==============================================================================
# 手順5 getlogs の起動
#   引数1 : ログフォルダ名プレフィックス
#==============================================================================
getlogs() {
    echo "[5] start getlogs"
    echo -n "    start getlogs ... "

    local lognamePrefix=$1


    # 実行するコマンドがほぼほぼrootじゃないと実行できない。

    # JMeter (ローカル)
    ${ATRS_SHELL_DIR}/getlogs.sh ${LOG_INTEVAL_SEC} ${LOG_COUNT} ${GETLOGS_DIR}/${lognamePrefix}_jmeterserver  >/dev/null 2>&1 &

    # TOMCAT側
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "${ATRS_SHELL_DIR}/getlogs.sh ${LOG_INTEVAL_SEC} ${LOG_COUNT} ${GETLOGS_DIR}/${lognamePrefix}_apserver >/dev/null 2>&1 & "

    # PostgreSQL側
    ssh ${POSTGRES_IP} -l ${ROOT_USER} -p ${DB_SSH_PORT} "${ATRS_SHELL_DIR}/getlogs.sh ${LOG_INTEVAL_SEC} ${LOG_COUNT} ${GETLOGS_DIR}/${lognamePrefix}_dbserver >/dev/null 2>&1 & "

    # ActiveMQ側
    ssh ${ACTIVEMQ_IP} -l ${ROOT_USER} -p ${MQ_SSH_PORT} "${ATRS_SHELL_DIR}/getlogs.sh ${LOG_INTEVAL_SEC} ${LOG_COUNT} ${GETLOGS_DIR}/${lognamePrefix}_mqserver >/dev/null 2>&1 & "
    echo "    done."
}

#==============================================================================
# 手順6 測定時間分処理を止めて計測を待つ
#==============================================================================
waitDuration() {
    echo "[6] wait Duration"

    # 実行時間分待つ
    echo "    wait ${JMETER_DURATION} seconds ... "
    sleep ${JMETER_DURATION}
    echo "    done."

}

#==============================================================================
# 手順6 jmeterのバッチ実行
#==============================================================================
jmeter() {
    echo "[6] invoke jmeter batch script"

    # MQリクエストを投げる速度が順々に上がっていってしまうので、時間を分けて投げる。
    local JMETER_THREADS=100
    local EXECUTE_SECONDS=310
    local INTERVAL_SECONDS=80
    local REST_SECONDS=`expr ${EXECUTE_SECONDS} - ${INTERVAL_SECONDS}`
    local LOOP_COUNT=5
    local LAST_SECONDS=`expr ${JMETER_DURATION} % ${EXECUTE_SECONDS}`
    local EXECUTE_NUMBER=`expr ${JMETER_DURATION} / ${EXECUTE_SECONDS}`
    echo "    JMETER_DURATION=${JMETER_DURATION}"
    echo "    EXECUTE_SECONDS=${EXECUTE_SECONDS}"
    echo "    INTERVAL_SECONDS=${INTERVAL_SECONDS}"
    echo "    LOOP_COUNT=${LOOP_COUNT}"
    echo "    LAST_SECONDS=${LAST_SECONDS}"
    echo "    EXECUTE_NUMBER=${EXECUTE_NUMBER}"

    # JMeterシナリオのバッチ実行
    echo "    start jmeter ... "
    rm -f ${JMETER_HOME}/bin/jmeter.log
    rm -f ${JMETER_HOME}/bin/log/jmeter_log.xml
    cd ${JMETER_HOME}/bin

    for SEQ in `seq 1 ${EXECUTE_NUMBER}`
    do
      echo "    ${SEQ}回目の実行"
      if [ `expr ${SEQ} % ${LOOP_COUNT}` -ne "0" ]; then
        sh ${JMETER_HOME}/bin/jmeter.sh -n -t ${ATRS_SCENARIO_DIR}/atrs-jms-scenario.jmx -Jthreads=${JMETER_THREADS} -Jipaddr=${ACTIVEMQ_IP} -Jport=${ACTIVEMQ_MQ_PORT} -Jparameter_dir=${ATRS_SCENARIO_DIR}/parameter -Jduration=${EXECUTE_SECONDS} -Jlogfile=${JMETER_HOME}/bin/log/jmeter_log.xml >/dev/null 2>&1
      else
        sh ${JMETER_HOME}/bin/jmeter.sh -n -t ${ATRS_SCENARIO_DIR}/atrs-jms-scenario.jmx -Jthreads=${JMETER_THREADS} -Jipaddr=${ACTIVEMQ_IP} -Jport=${ACTIVEMQ_MQ_PORT} -Jparameter_dir=${ATRS_SCENARIO_DIR}/parameter -Jduration=${REST_SECONDS} -Jlogfile=${JMETER_HOME}/bin/log/jmeter_log.xml >/dev/null 2>&1
        echo "    INTERVAL_SECONDS"
        sleep ${INTERVAL_SECONDS}
      fi
    done

    if [ ${LAST_SECONDS} -ne "0" ]; then
      echo "    LAST_SECONDS"
      sh ${JMETER_HOME}/bin/jmeter.sh -n -t ${ATRS_SCENARIO_DIR}/atrs-jms-scenario.jmx -Jthreads=${JMETER_THREADS} -Jipaddr=${ACTIVEMQ_IP} -Jport=${ACTIVEMQ_MQ_PORT} -Jparameter_dir=${ATRS_SCENARIO_DIR}/parameter -Jduration=${LAST_SECONDS} -Jlogfile=${JMETER_HOME}/bin/log/jmeter_log.xml >/dev/null 2>&1
    fi

    echo "    done."

}

#==============================================================================
# 手順7 TOMCATの停止
#   引数なし
#==============================================================================
stopTomcat() {
    echo "[7] stop Tomcat"
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "systemctl stop tomcat"
    sleep 10
    echo "    done."
}

#==============================================================================
# 手順8 アプリケーションのアンデプロイ
#   引数1 : アプリケーションアーカイブ名 (e.g. atrs)
#==============================================================================
undeploy() {
    echo "[8] undeploy"

    local appName=$1
    echo -n "    undeploy -> ${appFileName} ..."
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "rm -rf ${TOMCAT_HOME}/webapps/${appName}"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "rm ${TOMCAT_HOME}/webapps/${appName}.war"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "rm ${TOMCAT_HOME}/conf/Catalina/localhost/${appName}.xml"
    echo "    OK"
}


#==============================================================================
# 手順9 ActiveMQの停止
#   引数なし
#==============================================================================
stopActiveMQ() {
    echo "[9] stop ActiveMQ"
    ssh ${ACTIVEMQ_IP} -l ${ACTIVEMQ_USER} -p ${MQ_SSH_PORT} "${ACTIVEMQ_HOME}/bin/activemq stop"
    sleep 10
    echo "    done."
}

#==============================================================================
# 手順10 ログの退避
#
#   引数1 : アプリケーション名 (e.g. atrs.war)
#   引数2 : Message Listenerのスレッド数
#   引数3 : 実行回
#==============================================================================
escapeLogs() {
    echo "[10] escape logs"

    local appName=$1
    local concurrency=$2
    local loopCnt=$3

    # APサーバログの退避ディレクトリの作成
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "mkdir -m 777 -p /vagrant/tomcat"

    # Tomcatサーバログの退避
    echo -n "    rename "${TOMCAT_HOME}"/logs -> /vagrant/tomcat/logs_tomcat_${appName}_${concurrency}_${loopCnt} ... "
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "mv ${TOMCAT_HOME}/logs /vagrant/tomcat/logs_tomcat_${appName}_${concurrency}_${loopCnt}"
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "(cd /vagrant/tomcat ; tar zcf logs_tomcat_${appName}_${concurrency}_${loopCnt}.tar.gz logs_tomcat_${appName}_${concurrency}_${loopCnt}; rm -rf logs_tomcat_${appName}_${concurrency}_${loopCnt})"

    # Tomcatサーバログの出力先作成
    echo -n "    create tomcat log directory"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "mkdir -p ${TOMCAT_HOME}/logs"
    echo "    done."

    # アプリケーションログの退避
    echo -n "    rename /var/log/atrs/tomcat -> /vagrant/tomcat/logs_application_${appName}_${concurrency}_${loopCnt} ... "
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "cp -r -L /var/log/atrs/tomcat /vagrant/tomcat/logs_application_${appName}_${concurrency}_${loopCnt}"
    ssh ${TOMCAT_IP} -l ${ROOT_USER} -p ${AP_SSH_PORT} "(cd /vagrant/tomcat ; tar zcf logs_application_${appName}_${concurrency}_${loopCnt}.tar.gz logs_application_${appName}_${concurrency}_${loopCnt}; rm -rf logs_application_${appName}_${concurrency}_${loopCnt})"

    # アプリケーションログを削除
    echo -n "    delete application log"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} -p ${AP_SSH_PORT} "rm -f /var/log/atrs/tomcat/*"
    echo "    done."

    # ActiveMQサーバログの退避
    echo -n "    rename activemq.log -> activemq_${appName}_${concurrency}_${loopCnt}.log ... "
    ssh ${ACTIVEMQ_IP} -l ${ROOT_USER} -p ${MQ_SSH_PORT} "mv ${ATRS_LOG_HOME}/activemq/activemq.log /vagrant/activemq/activemq_${appName}_${concurrency}_${loopCnt}.log"
    echo "    done."

    # jmeter.logの退避
    echo -n "    rename jmeter.log -> jmeter.log.tomcat.${appName}.${concurrency}.${loopCnt}.log ... "
    mv ${JMETER_HOME}/bin/jmeter.log ${ATRS_LOG_HOME}/jmeter/jmeter.tomcat.${appName}.${concurrency}.${loopCnt}.log
    echo "    done."
 
    # jmeter結果の退避
    echo -n "    rename jmeter_log.xml -> jmeter_log.tomcat.${appName}_${concurrency}.${loopCnt}.xml ... "
    mv ${JMETER_HOME}/bin/log/jmeter_log.xml ${ATRS_LOG_HOME}/jmeter/jmeter_log.tomcat.${appName}_${concurrency}_${loopCnt}.xml
    echo "    done."
 
    echo
}

#==============================================================================
# mainループ
#==============================================================================

mkdir -p ${GETLOGS_DIR}

for cnt in `seq 1 ${RUNTIMES}`
do
    echo "#`date +\"%Y/%m/%d %H:%M:%S\"` JMS性能試験測定時間 ${JMETER_DURATION}秒 , 第 ${cnt} 回を開始します  "

    for archive in ${TARGETS[@]}
    do
        archive_no_ext=${archive%.war}
        archive_no_ext=${archive_no_ext%.ear}
        for concurrency in ${CONCURRENCIES[@]}
        do
            echo "# ${archive_no_ext} Message Listenerのスレッド数 ${concurrency} を開始します"

            initdb
            startActiveMQ
            deploy ${archive_no_ext}
            startTomcat ${archive_no_ext} ${concurrency}
            getlogs ${archive_no_ext}_${concurrency}_${cnt}
            if [ "${ONGOING_REGISTER_DATA}" = "register" ]; then
                jmeter
            else
                waitDuration
            fi
            stopTomcat
            undeploy ${archive_no_ext}
            stopActiveMQ
            escapeLogs ${archive_no_ext} ${concurrency} ${cnt}

            echo "# ${archive_no_ext} Message Listenerのスレッド数 ${concurrency} が完了しました"
        done
    done
done

################################ End of File #################################################

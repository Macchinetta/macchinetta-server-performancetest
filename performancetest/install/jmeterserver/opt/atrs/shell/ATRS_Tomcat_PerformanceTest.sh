#!/bin/sh

##############################################################################################
# 性能試験自動化スクリプト (Tomcat用)
#
# サーバの起動停止、デプロイ、JMeterの起動、getlogsの起動、ログの退避など、
# 性能試験で必要な一連の作業を自動化します。
##############################################################################################



#==============================================================================
# 多重負荷試験設定
#==============================================================================

#性能試験実施スレッド数パターン
THREADS=("1" "100" "200" "300" "400" "500")

#性能試験実施回数
RUNTIMES=3

#測定時間設定（秒） 30分
JMETER_DURATION=1800

#システムログ記録パラメータ
#ログ記録間隔秒数
LOG_INTEVAL_SEC=10

#ログ記録回数
LOG_COUNT=181


#==============================================================================
# 長安試験設定
#==============================================================================

#性能試験実施スレッド数パターン 200スレッド
#THREADS=("200")

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
GETLOGS_DIR=${ATRS_LOG_HOME}/getlogs

#==============================================================================
# 負荷ツールサーバ設定
#==============================================================================

#JMeterインストール先
JMETER_HOME=/opt/jmeter/apache-jmeter-2.13

#シナリオ格納フォルダ
ATRS_SCENARIO_DIR=${ATRS_HOME}/scenario

#==============================================================================
# APサーバ設定
#==============================================================================

#tomcat service管理ユーザ
TOMCAT_USER=root

#TOMCATサーバインストール先
TOMCAT_HOME=/opt/tomcat7

#TOMCATサーバアドレス
TOMCAT_IP=192.168.10.13

#WEBサーバPort
PORT=8080

#atrsデプロイ資材格納フォルダ
ATRS_DEPLOY_DIR=${ATRS_HOME}/deploy/tomcat


#==============================================================================
# DBサーバ設定
#==============================================================================

#DBサーバログインユーザー
POSTGRES_USER=postgres

#PostgreSQLサーバアドレス
POSTGRES_IP=192.168.10.14

#テストデータ作成SQL格納フォルダー
TEST_DATA_HOME=${ATRS_HOME}/testdata

#==============================================================================
# 手順1 ATRS業務DBの 初期設定
#==============================================================================
initdb() {
    echo "[1] initialize table"
    
    ssh ${POSTGRES_IP} -l ${POSTGRES_USER} "${ATRS_SHELL_DIR}/generateTestData.sh >/dev/null 2>&1"
    echo 
}


#==============================================================================
# 手順2 アプリケーションのデプロイ
#   引数1 : デプロイ対象のアーカイブ名 (e.g. atrs),拡張子は不要
#==============================================================================
deploy() {
    echo "[2] deploy application"

    local appName=$1
    echo "    deploy ${appName}.war"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "cp ${ATRS_DEPLOY_DIR}/${appName}.war ${TOMCAT_HOME}/webapps/"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "cp ${ATRS_DEPLOY_DIR}/${appName}.xml ${TOMCAT_HOME}/conf/Catalina/localhost/"
    echo
}

#==============================================================================
# 手順3 Tomcatの起動
#   引数1 : デプロイ対象のアーカイブ名 (e.g. atrs),拡張子は不要
#==============================================================================
startTomcat() {
    echo "[3] start Tomcat7"
    
    local appName=$1

    # Tomcat サービス起動
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "service tomcat7 start"

    echo -n "    wait 30sec"
    for i in $(seq 1 6)
    do
      sleep 5
      echo -n .
    done

    # wgetでトップページを取得することでデプロイ正常性確認
    wget http://${TOMCAT_IP}:${PORT}/${appName}/ >/dev/null 2>&1

    if [ $? -eq  0 ];
    then
        echo "    Tomcat start success : "${appName}
        #rm index.html
    else
        echo "    Tomcat start  failed : "${appName}
        exit 1
    fi
    echo
    echo
}

#==============================================================================
# 手順4 getlogs の起動
#   引数1 : ログフォルダ名プレフィックス
#==============================================================================
getlogs() {
    echo "[4] start getlogs"
    echo -n "    start getlogs ... "
    
    local lognamePrefix=$1
    

    # JMeter (ローカル)
    ${ATRS_SHELL_DIR}/getlogs.sh ${LOG_INTEVAL_SEC} ${LOG_COUNT} ${GETLOGS_DIR}/${lognamePrefix}_jmeterserver  >/dev/null 2>&1 & 
    
    # TOMCAT側
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "${ATRS_SHELL_DIR}/getlogs.sh ${LOG_INTEVAL_SEC} ${LOG_COUNT} ${GETLOGS_DIR}/${lognamePrefix}_apserver.log >/dev/null 2>&1 & "
    
    # PostgreSQL側
    ssh ${POSTGRES_IP} -l ${POSTGRES_USER} "${ATRS_SHELL_DIR}/getlogs.sh ${LOG_INTEVAL_SEC} ${LOG_COUNT} ${GETLOGS_DIR}/${lognamePrefix}_dbserver.log >/dev/null 2>&1 & "
    echo "    done."
    echo
}

#==============================================================================
# 手順5 jmeterのバッチ実行
#   引数1 : スレッド数 
#==============================================================================
jmeter() {
    echo "[5] invoke jmeter batch script"

    local thread=$1
    # JMeterシナリオのバッチ実行
    echo -n "    start jmeter ... "
    rm -f ${JMETER_HOME}/bin/jmeter.log
    rm -f ${JMETER_HOME}/bin/log/jmeter_log.xml
    cd ${JMETER_HOME}/bin
    sh ${JMETER_HOME}/bin/jmeter.sh -n -t ${ATRS_SCENARIO_DIR}/atrs-scenario.jmx -Jthreads=${thread} -Jipaddr=${TOMCAT_IP} -Jport=${PORT} -Jparameter_dir=${ATRS_SCENARIO_DIR}/parameter -Jduration=${JMETER_DURATION} -Jlogfile=${JMETER_HOME}/bin/log/jmeter_log.xml >/dev/null 2>&1
    echo "    done."
    echo
}

#==============================================================================
# 手順6 TOMCATの停止
#   引数なし
#==============================================================================
stopTomcat() {
    echo "[6] stop Tomcat7"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "service tomcat7 stop"
    echo
}

#==============================================================================
# 手順7 アプリケーションのアンデプロイ
#   引数1 : アプリケーションアーカイブ名 (e.g. atrs)
#==============================================================================
undeploy() {
    echo "[7] undeploy"

    local appName=$1
    echo -n "    undeploy -> "${appFileName}"..."
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "rm -rf ${TOMCAT_HOME}/webapps/${appName}"
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "rm ${TOMCAT_HOME}/webapps/${appName}.war"
    echo "    OK"
    echo
}


#==============================================================================
# 手順8 ログの退避
# 
#   引数1 : アプリケーション名 (e.g. atrs.war)
#   引数2 : スレッド数
#   引数3 : 実行回
#==============================================================================
escapeLogs() {
    echo "[8] escape logs"

    local appName=$1
    local thread=$2
    local loopCnt=$3

    # Tomcatサーバログの退避
    echo -n "    rename "${TOMCAT_HOME}"/logs -> ${ATRS_LOG_HOME}/tomcat/logs_tomcat_"${appName}"_"${thread}"_"${loopCnt}" ... "
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "cp -r -L ${TOMCAT_HOME}/logs ${ATRS_LOG_HOME}/tomcat/logs_tomcat_${appName}_${thread}_${loopCnt}"
    
    # Tomcatサーバログを削除
    ssh ${TOMCAT_IP} -l ${TOMCAT_USER} "rm -f ${TOMCAT_HOME}/logs/*"
    echo "    done."

    # jmeter.logの退避
    echo -n "    rename jmeter.log -> jmeter.log.tomcat."${appName}"."${thread}" ... "
    mv ${JMETER_HOME}/bin/jmeter.log ${ATRS_LOG_HOME}/jmeter/jmeter.tomcat.${appName}.${thread}.${loopCnt}.log
    echo "    done."

    # jmeter結果の退避
    echo -n "    rename jmeter_log.xml -> tomcat.${appName}_${thread}.xml ... "
    mv ${JMETER_HOME}/bin/log/jmeter_log.xml ${ATRS_LOG_HOME}/jmeter/jmeter_log.tomcat.${appName}_${thread}_${loopCnt}.xml
    echo "    done."

    echo
}


#==============================================================================
# mainループ
#==============================================================================

for cnt in `seq 1 ${RUNTIMES}`
do
    echo "#`date +\"%Y/%m/%d %H:%M:%S\"` 性能試験測定時間 ${JMETER_DURATION}秒 , 第 ${cnt} 回を開始します  " 
    
    for archive in ${TARGETS[@]}
    do
        archive_no_ext=${archive%.war}
        archive_no_ext=${archive_no_ext%.ear}
        for thread in ${THREADS[@]}
        do
            echo "# "${archive_no_ext}" スレッド数 ${thread} を開始します" 
            
            initdb
            deploy ${archive_no_ext}
            startTomcat ${archive_no_ext}
            getlogs ${archive_no_ext}_${thread}_${cnt}
            jmeter ${thread}
            stopTomcat
            undeploy ${archive_no_ext}
            escapeLogs ${archive_no_ext} ${thread} ${cnt}
            
            echo "# "${archive_no_ext}" スレッド数 ${thread} が完了しました" 
        done
    done
    echo "# 性能試験第 ${cnt} 回が完了しました" 
done






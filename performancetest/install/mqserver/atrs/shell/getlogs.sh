#!/bin/sh
#----------------------------------------------------------------
# getlogs.sh {wait_time(sec.)} {loop_count} {output_folder}
#----------------------------------------------------------------

# init ----------------------------------------------------------
HOST_NAME=`hostname -a`
WAIT_SEC=$1
LOOP_COUNT=$2
OUT_DIR=$3

mkdir -p ${OUT_DIR} > /dev/null

OUT_VMSTAT_FILE="${OUT_DIR}/vmstat.csv"
OUT_IOSTAT_FILE="${OUT_DIR}/iostat.csv"
OUT_SAR_FILE="${OUT_DIR}/sar.rpt"
OUT_PS_FILE="${OUT_DIR}/ps.csv"
OUT_START_FILE="${OUT_DIR}/start.log"
OUT_END_FILE="${OUT_DIR}/end.log"

# function
funcWriteLog () {
	echo "--------------------------------------------------" >> $2
	echo "|| $1 log" >> $2
	echo "--------------------------------------------------" >> $2
	echo "`$1`" >> $2
	echo "" >> $2
}

# write start log -------------------------------------------------
OUT_FILE=${OUT_START_FILE}
echo "" > ${OUT_FILE}
funcWriteLog "uname -a" ${OUT_FILE}
funcWriteLog "dmesg" ${OUT_FILE}
funcWriteLog "free" ${OUT_FILE}
funcWriteLog "uptime" ${OUT_FILE}
funcWriteLog "w" ${OUT_FILE}
funcWriteLog "ipcs -a" ${OUT_FILE}
funcWriteLog "ifconfig -a" ${OUT_FILE}
funcWriteLog "df -k" ${OUT_FILE}
funcWriteLog "netstat -rn" ${OUT_FILE}
funcWriteLog "netstat -s" ${OUT_FILE}

# vmstat header --------------------------------------------------
CSV_DATA=`vmstat | awk 'NR==2' | tr -s ' ' ','`
echo "hostname,datetime,${CSV_DATA}" > ${OUT_VMSTAT_FILE}

# iostat header
CSV_DATA=`iostat -d -x | awk 'NR==3' | tr -s ' ' ','`
echo "hostname,datetime,${CSV_DATA}" > ${OUT_IOSTAT_FILE}

# ps file clear
PS_PARAM="-eo uid,user,gid,group,pid,ppid,f,s,nice,vsz,rsz,rss,nlwp,stime,etime,time,tty,pmem,pcpu,args"
CSV_DATA=`ps ${PS_PARAM} | awk 'NR==1' | tr -s ' ' ','`
echo "hostname,datetime,${CSV_DATA}" > ${OUT_PS_FILE}

# sar file
`/usr/lib64/sa/sadc ${WAIT_SEC} ${LOOP_COUNT} ${OUT_DIR}/sar.dat` &


# Main Loop -----------------------------------------------------
while [ ${LOOP_COUNT} -gt 0 ]
do
    DATE_TIME=`date +"%Y-%m-%d %H:%M:%S"`
	# vmstat -----------------------------------------------------
    CSV_DATA=`vmstat | awk 'NR==3' | tr -s ' ' ','`
    echo ${HOST_NAME},${DATE_TIME},${CSV_DATA} >> ${OUT_VMSTAT_FILE}
    
	# iostat 
	CSV_DATA=`iostat -d -x | awk 'NR>=4' | tr -s ' ' ','`
	echo "${HOST_NAME},${DATE_TIME},${CSV_DATA}" >> ${OUT_IOSTAT_FILE}
    
    # ps
    CSV_DATA=`ps ${PS_PARAM} | awk 'NR>=2' |tr -s ' ' ','`
    for LINE in ${CSV_DATA}; do
    	echo "${HOST_NAME},${DATE_TIME},${LINE}" >> ${OUT_PS_FILE}
    done
    

    LOOP_COUNT=$((${LOOP_COUNT} - 1))
    sleep ${WAIT_SEC}
done

# make sar file
echo "`sar -A -f ${OUT_DIR}/sar.dat`" > ${OUT_SAR_FILE}

# write end log -------------------------------------------------
OUT_FILE=${OUT_END_FILE}
echo "" > ${OUT_FILE}
funcWriteLog "uname -a" ${OUT_FILE}
funcWriteLog "dmesg" ${OUT_FILE}
funcWriteLog "free" ${OUT_FILE}
funcWriteLog "uptime" ${OUT_FILE}
funcWriteLog "w" ${OUT_FILE}
funcWriteLog "ipcs -a" ${OUT_FILE}
funcWriteLog "ifconfig -a" ${OUT_FILE}
funcWriteLog "df -k" ${OUT_FILE}
funcWriteLog "netstat -rn" ${OUT_FILE}
funcWriteLog "netstat -s" ${OUT_FILE}

#!/bin/sh
#----------------------------------------------------------------
# 性能試験DBテストデータ初期化シェル
#----------------------------------------------------------------
ATRS_HOME=/opt/atrs


#SQLファイル格納フォルダー
SQL_DIR=${ATRS_HOME}/sqls/performanceTestPostgres

#DBログインユーザー名
DB_USER=postgres

#Database名
DB_NAME=atrs

#DBサーバアドレス設定
DB_HOST=127.0.0.1
DB_PORT=5432


SET PGCLIENTENCODING=UTF-8

psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00000_drop_all_tables.sql
psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00100_create_all_tables.sql
psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00200_insert_fixed_value.sql
psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00210_insert_route.sql
psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00220_insert_flight_master.sql
psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00230_insert_member.sql
psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00240_insert_peak_time.sql
psql -U ${DB_USER} -d ${DB_NAME} -h${DB_HOST} -p${DB_PORT} -f ${SQL_DIR}/00250_insert_flight.sql



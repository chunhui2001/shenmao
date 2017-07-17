
# Crontab on Ubuntu
=======================
# list crontabs
$ crontab -e 

# Creates the file:
$ echo "30 17 * * 1 /path/to/command" > /var/spool/cron/crontabs/root

# change owner:
chown root:root /var/spool/cron/crontabs/root

# change permissions:
chmod 600 /var/spool/cron/crontabs/root

# example 
*/1 * * * * /home/keesh/workspace/ebid-web/reload.sh >> /var/log/mylog.log 2>&1			


# reload
/etc/init.d/cron reload


# example
*/1 * * * * 			# 每分钟执行一次
0 0,6,12,18 * * * 		# 0, 6, 12 and 18 每 6 小时执行一次
0 */6 * * * 			# 每 6 小时执行一次



# 查出 node 进程并杀死
ps -ef | grep node | grep -v grep | awk '{print $2}' | xargs kill
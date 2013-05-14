cd /var/lib/tomcat7/webapps/virtual_lab/
sudo chown  :web-editor *.html
sudo chown -R  :web-editor css/
sudo chown -R  :web-editor js/
sudo chown -R  :web-editor opensocial/
sudo chown -R  :web-editor history/
sudo chown -R  :web-editor lib/
sudo chown -R  :web-editor partials/
sudo chown -R  :web-editor images/
sudo chown -R  :web-editor projects/
sudo chown -R  :web-editor img/
sudo chown -R  :tool-tester toolLibrary_new/
sudo chown -R :tool-tester toolLibrary/

sudo mkdir /huni_data
sudo mkdir /huni_backup
sudo chown tomcat7:tomcat7 /huni_data/
sudo cp /huni_data/toolLibrary.0.tgz /huni_data
cd /huni_data
sudo tar -xvf toolLibrary.0.tgz
sudo rm toolLibrary.0.tgz

cd /var/lib/tomcat7/webapps/virtual_lab/
sudo ln -s /huni_data/toolLibrary toolLibrary
sudo chown -h :tool-tester toolLibrary
sudo chown -R :tool-tester toolLibrary/

cd /var/lib/tomcat7/webapps/virtual_lab/META-INF
sudo vi context.xml

<?xml version="1.0" encoding="UTF-8"?>
<Context path="/myapp" allowLinking="true">
</Context>

cd /var/lib/tomcat7/webapps/virtual_lab/WEB-INF/classes/META-INF/spring
sudo vi database.properties

database.url=jdbc\:derby\:/huni_data/HuNI_Database;create\=true


service tomcat7 restart

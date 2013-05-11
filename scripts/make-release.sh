cd ../kos-client/
mvn clean install
cd ../reposapi/
mvn clean install
cd ../bupro/
mvn clean install -Dmaven.test.skip=true
cd ../scripts/

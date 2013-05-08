cd ../kos-client/
mvn install
cd ../reposapi/
mvn install
cd ../bupro/
mvn install -Dmaven.test.skip=true
cd ../scripts/

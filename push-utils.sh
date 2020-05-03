version=1.0.10-RELEASE
mvn clean
mvn deploy -DaltDeploymentRepository=oozol-mvn-repo::default::file:mvn/repository
#mvn package
#cd ./target/
#mvn install:install-file -Dfile=utils-$version.jar -DgroupId=com.oozol -DartifactId=utils -Dversion=$version -Dpackaging=jar

cd mvn
git init
git remote add origin https://github.com/xiongzhaoxu/utils.git
git remote set-url origin https://github.com/xiongzhaoxu/utils.git
git pull origin master --allow-unrelated-histories
git add .
git commit -m "add version"
git push -u origin master
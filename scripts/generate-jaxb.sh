#
# Script to generate JAXB for KOS
#
project=bupro
xjc -d ../$project/src/main/java/ -p cz.cvut.fel.kos.jaxb ../$project/src/main/resources/jaxb/kos.xsd -b ../$project/src/main/resources/jaxb/bindings.xjb

#
# Script to generate JAXB for KOS
#
xjc -d ../bupro/src/main/java/ -p cz.cvut.fel.kos.jaxb ../bupro/src/main/resources/jaxb/kos.xsd

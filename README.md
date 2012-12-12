ddsc-aquo
=========

A Java command-line application for synchronizing with [Aquo DS](http://www.aquo.nl/aquo-standaard/aquo-domeintabellen/).

Building ddsc-aquo
------------------

Being a NetBeans project, ddsc-aquo is most conveniently managed via the [NetBeans IDE](http://netbeans.org/). Alternatively, if you just want to build the project and have [Ant](http://ant.apache.org/) installed (e.g. `sudo apt-get install ant`), you can navigate to the project root directory and type `ant` at the command line. Before building, you'll need to create a persistence.xml in src/META-INF. An example, [persistence.xml.template](https://github.com/ddsc/ddsc-aquo/blob/master/src/META-INF/persistence.xml.template), has been included.
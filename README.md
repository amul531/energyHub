# energyHub
 Bash cartesian product in java: </br>
 Example:</br>
 #1.</br>
 input:a{b,c}d{e,f,g}hi</br>
 output:abdehi abdfhi abdghi acdehi acdfhi acdghi</br>
 #2.</br>
 input:a{b,c{d,e,f}g,h}ij{k,l}</br>
 output:abijk abijl acdgijk acdgijl acegijk acegijl acfgijk acfgijl ahijk ahijl
 
#BashCartesian
  This is the cleaned up version of the code with two different ways to test it.</br>
  Clone or download this program and run it through command line or any java editor of your choice.</br>
  Uncomment any testing block of choice in the main function before compiling and running it. 
  
#bcTest(running it from command line)
  This is the junit test for BashCartesian.</br>
  To run this, you need the following set up:</br>
  1)Clone or download the entire repository ( it contains the necessary jars to run the test).</br>
  2)"cd ..." to the downloaded folder.</br>
  3)javac BashCartesian.java  (to compile the main program)</br>
  4)Compile the test:</br>
      -On Linux or MacOS: javac -cp .:junit-4.12.jar bcTest.java</br>
      -On Windows: javac -cp .;junit-4.12.jar bcTest.java</br>
  5)Run the test: </br>
      -On Linux or MacOS: java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore bcTest</br>
      -On Windows: java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore bcTest

import static org.junit.Assert.*;
import org.junit.Test;

public class bcTest { 	
	String inputStr;
	String[] inputArr;
	String[] outputArr;
	
	@Test
	public void test1_noNest() {
        inputArr = BashCartesian.bashCartesianProduct("ab{c,d}e").split(",");
        outputArr = "abce abde".split(" ");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test
	public void test2_noNest() {
        inputArr = BashCartesian.bashCartesianProduct("a{b,c}d{e,f}g").split(",");
        outputArr = "abdeg,abdfg,acdeg,acdfg".split(",");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test
	public void test3_noNest() {
        inputArr = BashCartesian.bashCartesianProduct("{a,b}{c,d}").split(",");
        outputArr = "ac,ad,bc,bd".split(",");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test
	public void test4_noNest() {
        inputArr = BashCartesian.bashCartesianProduct("{a,b}x{c,d}").split(",");
        outputArr = "axc axd bxc bxd".split(" ");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test
	public void test5_Nest() {
        inputArr = BashCartesian.bashCartesianProduct("a{b,c{d,e}f,g}hi{j,k}").split(",");
        outputArr = "abhij,abhik,acdfhij,acdfhik,acefhij,acefhik,aghij,aghik".split(",");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test
	public void test6_Nest() {
        inputArr = BashCartesian.bashCartesianProduct("{{{{a,b},c}d,e}f,g}").split(",");
        outputArr = "adf,bdf,cdf,ef,g".split(",");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test
	public void test7_Nest() {
        inputArr = BashCartesian.bashCartesianProduct("ab{c,d{e,f,g}}hi").split(",");
        outputArr = "abchi abdehi abdfhi abdghi".split(" ");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test
	public void test8_Nest() {
        inputArr = BashCartesian.bashCartesianProduct("a{b,c{d,e{k,l}f}g,h}ij").split(",");
        outputArr = "abij acdgij acekfgij acelfgij ahij".split(" ");
        for(int i=0;i<inputArr.length;i++)
        	assertTrue(inputArr[i].equals(outputArr[i]));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test9_exception() {
		inputArr = BashCartesian.bashCartesianProduct("{{{{a,b},c}d,ef,g}").split(",");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test10_exception() {
		inputArr = BashCartesian.bashCartesianProduct("{{a,b,c,d,ef,g}").split(",");
	}

}
    

/**
 * The class containing your tests for the {@link Demo} class.  Make sure you
 * test all methods in this class (including both 
 * {@link Demo#main(String[])} and 
 * {@link Demo#isTriangle(double, double, double)}).
 */
public class DemoTest {
	// Import statements
	// (Placed inside file to adhere to simple single-file structure; normally at top.)
	@org.junit.Test
	public void testTypicalValidTriangles() {
		org.junit.Assert.assertTrue(Demo.isTriangle(3, 4, 5));
		org.junit.Assert.assertTrue(Demo.isTriangle(5, 3, 4)); // permutation
		org.junit.Assert.assertTrue(Demo.isTriangle(4, 5, 3)); // another permutation
		org.junit.Assert.assertTrue(Demo.isTriangle(2, 2, 3)); // isosceles
		org.junit.Assert.assertTrue(Demo.isTriangle(0.3, 0.4, 0.5)); // fractional
	}

	@org.junit.Test
	public void testInequalityFailures_individual() {
		// a + b <= c (boundary equality)
		org.junit.Assert.assertFalse(Demo.isTriangle(2, 3, 5));
		org.junit.Assert.assertFalse(Demo.isTriangle(1, 2, 3));

		// a + c <= b
		org.junit.Assert.assertFalse(Demo.isTriangle(2, 6, 3)); // 2 + 3 = 5 <= 6

		// b + c <= a
		org.junit.Assert.assertFalse(Demo.isTriangle(10, 3, 4)); // 3 + 4 = 7 <= 10
	}

	@org.junit.Test
	public void testZeroOrNegativeSides() {
		org.junit.Assert.assertFalse(Demo.isTriangle(0, 1, 1));
		org.junit.Assert.assertFalse(Demo.isTriangle(1, 0, 1));
		org.junit.Assert.assertFalse(Demo.isTriangle(1, 1, 0));
		org.junit.Assert.assertFalse(Demo.isTriangle(-1, 2, 3));
		org.junit.Assert.assertFalse(Demo.isTriangle(2, -1, 3));
		org.junit.Assert.assertFalse(Demo.isTriangle(2, 3, -1));
	}

	@org.junit.Test
	public void testSymmetryRandomSets() {
		double[][] sets = {
			{7, 8, 9},
			{2, 5, 6},
			{4, 4, 5}
		};
		for (double[] s : sets) {
			org.junit.Assert.assertTrue(Demo.isTriangle(s[0], s[1], s[2]));
			org.junit.Assert.assertTrue(Demo.isTriangle(s[0], s[2], s[1]));
			org.junit.Assert.assertTrue(Demo.isTriangle(s[1], s[0], s[2]));
			org.junit.Assert.assertTrue(Demo.isTriangle(s[1], s[2], s[0]));
			org.junit.Assert.assertTrue(Demo.isTriangle(s[2], s[0], s[1]));
			org.junit.Assert.assertTrue(Demo.isTriangle(s[2], s[1], s[0]));
		}
	}

	@org.junit.Test
	public void testBoundaryEqualityNotTriangleAllPermutations() {
		double a=1, b=2, c=3; // equality case should always be false
		double[][] perms = {
			{a,b,c},{a,c,b},{b,a,c},{b,c,a},{c,a,b},{c,b,a}
		};
		for (double[] p : perms) {
			org.junit.Assert.assertFalse("Equality case should not be a triangle for permutation: "+p[0]+","+p[1]+","+p[2],
				Demo.isTriangle(p[0], p[1], p[2]));
		}
	}

	@org.junit.Test
	public void testMainOutputsTriangle() throws Exception {
		String input = "3\n4\n5\n"; // newline separated inputs
		java.io.InputStream originalIn = System.in;
		java.io.PrintStream originalOut = System.out;
		java.io.ByteArrayInputStream testIn = new java.io.ByteArrayInputStream(input.getBytes());
		java.io.ByteArrayOutputStream testOut = new java.io.ByteArrayOutputStream();
		try {
			System.setIn(testIn);
			System.setOut(new java.io.PrintStream(testOut));
			Demo.main(new String[]{});
		} finally {
			System.setIn(originalIn);
			System.setOut(originalOut);
		}
		String out = testOut.toString();
		org.junit.Assert.assertTrue("Should prompt for side 1", out.contains("Enter side 1"));
		org.junit.Assert.assertTrue("Should prompt for side 2", out.contains("Enter side 2"));
		org.junit.Assert.assertTrue("Should prompt for side 3", out.contains("Enter side 3"));
		org.junit.Assert.assertTrue("Should identify triangle", out.contains("This is a triangle."));
	}

	@org.junit.Test
	public void testMainOutputsNotTriangle() throws Exception {
		String input = "1\n2\n3\n"; // equality case
		java.io.InputStream originalIn = System.in;
		java.io.PrintStream originalOut = System.out;
		java.io.ByteArrayInputStream testIn = new java.io.ByteArrayInputStream(input.getBytes());
		java.io.ByteArrayOutputStream testOut = new java.io.ByteArrayOutputStream();
		try {
			System.setIn(testIn);
			System.setOut(new java.io.PrintStream(testOut));
			Demo.main(new String[]{});
		} finally {
			System.setIn(originalIn);
			System.setOut(originalOut);
		}
		String out = testOut.toString();
		org.junit.Assert.assertTrue(out.contains("This is not a triangle."));
	}
}

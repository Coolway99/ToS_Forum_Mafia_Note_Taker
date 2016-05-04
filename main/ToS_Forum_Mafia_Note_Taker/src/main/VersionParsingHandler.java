package main;

// TODO rewrite this whole file
class VersionParsingHandler{
	public static boolean isVersionLessThan(String isThis, String lessThanThis){
		int[] A = parseVersion(isThis);
		int[] B = parseVersion(lessThanThis);
		if(A[0] < B[0]) {
			return true;
		}
		if(A[0] == B[0]) {
			if(A[1] < B[1]) {
				return true;
			}
			if(A[1] == B[1]) {
				if(A[2] < B[2]) {
					return true;
				}
				if(A[2] == B[2]) {
					if(A[3] < B[3]) {
						return true;
					}
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	public static boolean isVersionEqualToo(String isThis, String equalToThis){
		int[] A = parseVersion(isThis);
		int[] B = parseVersion(equalToThis);
		if(A[0] == B[0]) {
			if(A[1] == B[1]) {
				if(A[2] == B[2]) {
					if(A[3] == B[3]) {
						return true;
					}
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	public static boolean isVersionGreaterThan(String isThis, String greaterThanThis){
		int[] A = parseVersion(isThis);
		int[] B = parseVersion(greaterThanThis);
		if(A[0] > B[0]) {
			return true;
		}
		if(A[0] == B[0]) {
			if(A[1] > B[1]) {
				return true;
			}
			if(A[1] == B[1]) {
				if(A[2] > B[2]) {
					return true;
				}
				if(A[2] == B[2]) {
					if(A[3] > B[3]) {
						return true;
					}
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	private static int[] parseVersion(String Version){
		String[] unParsed = Version.split("\\.");
		int[] version = new int[unParsed.length];
		for(int x = 0; x < version.length; x ++) {
			version[x] = Integer.parseInt(unParsed[x]);
		}
		return version;
	}
}
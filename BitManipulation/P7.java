/*
Imagine you're designing a sequence of signals for a high-tech robot. 
The robot receives its instructions as list of integers, where each integer 
represents one byte of the command data. A complete command can be composed of 
1 to 4 bytes, following these strict rules:

- For a 1-byte command, the first bit must be 0, followed by the command's code.
- For a multi-byte command (with n bytes), the first byte starts with n 
  consecutive 1’s, immediately followed by a 0. Each of the following n – 1 bytes 
  must begin with the bit pattern 10.

This is how the robot interprets the byte sequences:

 Number of Bytes   |        Robot Signal Sequence
				   |              (binary)
-------------------+---------------------------------------
		1          |   0xxxxxxx
		2          |   110xxxxx 10xxxxxx
		3          |   1110xxxx 10xxxxxx 10xxxxxx
		4          |   11110xxx 10xxxxxx 10xxxxxx 10xxxxxx

Here, each x represents a bit that can be either 0 or 1.

Note: Only the least significant 8 bits of each integer in the input list of 
integers are used, meaning each integer stands for exactly one byte of data.

Your challenge is to verify whether the provided sequence of robot instructions 
strictly follows the defined encoding rules.

Example 1:
----------
Input=
197 130 1

Output=
true

Explanation: 
- The array corresponds to the binary sequence: 11000101 10000010 00000001.  
- This is a valid encoding: a 2-byte command (11000101 10000010) followed by a 
  valid 1-byte command (00000001).

Example 2:
----------
Input=
234 140 4

Output=
false

Explanation:
- The array corresponds to the binary sequence: 11101011 10001100 00000100.  
- The first three bits of the first byte are 1’s with a following 0, indicating 
  a 3-byte command. The second byte starts correctly with 10, but the third byte 
  does not begin with 10, so the command sequence is invalid.

Constraints:

- 1 <= instructions.length <= 2 * 10^4
- 0 <= instruction <= 255

*/
import java.util.*;

class Solution {
    // This method verifies if the instruction sequence is valid.
    public static boolean validEncoding(int[] data) {
        int n = data.length;
        int i = 0;
        while (i < n) {
            int firstByte = data[i];
            // Count the number of leading 1's in the current byte.
            int count = 0;
            int mask = 1 << 7; // Mask to check the most significant bit
            while ((mask & firstByte) != 0) {
                count++;
                mask >>= 1;
            }
            // For a 1-byte command, the leading bit must be 0.
            if (count == 0) {
                i++;
                continue;
            }
            // If the count is 1 or greater than 4, it's invalid.
            if (count == 1 || count > 4) {
                return false;
            }
            // Check that there are exactly (count - 1) continuation bytes following.
            if (i + count > n) {
                return false;
            }
            // Each continuation byte must start with '10'
            for (int j = i + 1; j < i + count; j++) {
                // The byte must have a '1' in the 7th bit and '0' in the 6th bit.
                if ((data[j] & (1 << 7)) == 0 || (data[j] & (1 << 6)) != 0) {
                    return false;
                }
            }
            i += count;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Read the input as a line of integers separated by space.
        String[] tokens = sc.nextLine().split(" ");
        int[] data = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            data[i] = Integer.parseInt(tokens[i]);
        }
        System.out.println(validEncoding(data));
    }
}

package pl.samba.lms.utils;

public class Util {

    public static String getFileExtension(byte[] fileBytes) {
        if (fileBytes == null || fileBytes.length < 2) {
            // Not enough bytes to determine the file extension
            return null;
        }

        // Get the first two bytes of the file
        byte byte1 = fileBytes[0];
        byte byte2 = fileBytes[1];

        // Check for common file signatures
        if (byte1 == (byte) 0xFF && byte2 == (byte) 0xD8) {
            return "jpg";
        } else if (byte1 == (byte) 0x89 && byte2 == (byte) 0x50) {
            return "png";
        } else if (byte1 == (byte) 0x47 && byte2 == (byte) 0x49) {
            return "gif";
        } // Add more file types as needed

        // If no specific signature is matched, return null
        return null;
    }
}

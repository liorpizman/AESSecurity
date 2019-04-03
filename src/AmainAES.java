import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This abstract class represents AES1 and AES3 basics
 */
public abstract class AmainAES {

    /**
     * Fields of AES1 and AES3 basics
     */
    protected final int Nb = 4;
    protected final int blockBytes = 16;

    protected byte[][] CipherKey1;
    protected byte[][] CipherKey2;
    protected byte[][] CipherKey3;

    protected static ConvertByte C;

    protected String outPath;

    protected abstract void SplitToFields(String keyPath, String cipherText);

    /**
     * Separate three cipher keys from input file
     * @param mKey byte array of merged keys
     */
    protected void SeparateKeys(byte[] mKey) {
        byte[] k1 = new byte[blockBytes];
        byte[] k2 = new byte[blockBytes];
        byte[] k3 = new byte[blockBytes];

        InitialKey(mKey, k1, 0);               // 0  - index
        InitialKey(mKey, k2, blockBytes);              // 16 - index
        InitialKey(mKey, k3, blockBytes * 2);  // 32 - index

        C.Append(CipherKey1, k1);
        C.Append(CipherKey2, k2);
        C.Append(CipherKey3, k3);
    }

    /**
     * Creates key from byte array
     * @param mKey main key
     * @param key new key
     * @param startIndex index to start from
     */
    protected void InitialKey(byte[] mKey, byte[] key, int startIndex) {
        for (int i = 0; i < blockBytes; i++) {
            key[i] = mKey[startIndex + i];
        }
    }

    protected abstract void ShiftRows(byte[][] state);

    /**
     * Making XOR operation between two byte arrays
     * @param state first byte array
     * @param cipherKey second byte array
     * @return byte array after XOR
     */
    protected byte[][] AddRoundKey(byte[][] state, byte[][] cipherKey) {
        byte[][] roundKey = new byte[Nb][Nb];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                roundKey[j][i] = (byte) (state[j][i] ^ cipherKey[j][i]);
        return roundKey;
    }

    /**
     * Writes byte array to file
     * @param bRes byte array to write
     * @param destPath path of new file
     */
    protected void WriteOutput(byte[] bRes, String destPath){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(destPath);
            fileOutputStream.write(bRes);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

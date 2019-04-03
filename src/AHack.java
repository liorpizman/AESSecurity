import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This abstract class represents Hack basic algorithm of AES
 */
public abstract class AHack {
    /**
     * Fields of AHack
     */
    protected final int Nb = 4;
    protected final int blockBytes = 16;

    protected byte[][] CipherKey1;
    protected byte[][] CipherKey2;
    protected byte[][] CipherKey3;

    protected String outPath;
    protected static ConvertByte C;

    protected byte[] m_Msg;
    protected byte[] m_Cipher;

    public abstract void RunHack();

    /**
     * Creates two byte array with random values
     * @return byte array
     */
    protected byte[] CreateRandomBytesArray() {
        byte[] bytes = new byte[4];
        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Shift left of Rows of matrix by steps
     *
     * @param state current byte array
     */
    public byte[][] ShiftRows(byte[][] state) {/* Shift left */
        int i = 0;
        byte[][] newKey = new byte[4][Nb];
        for (int j = 0; j < 4; j++)
            newKey[i][j] = state[i][j];
        i++;
        for (byte[] tmp = new byte[4]; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                tmp[j] = state[i][(j + i) % Nb];
            for (int m = 0; m < 4; m++)
                newKey[i][m] = tmp[m];
        }
        return newKey;
    }

    /**
     * Writes byte array to file
     * @param bRes byte array to write
     * @param destPath path of new file
     */
    protected void WriteOutput(byte[] bRes, String destPath) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract byte[][] XorWithCipher(byte[][] tmpKey, byte[][] cipher);

    protected abstract byte[] CombineKeys();

    protected abstract void MergeByteArray(byte[] cipherKey, byte[] key, int startIndex);

}

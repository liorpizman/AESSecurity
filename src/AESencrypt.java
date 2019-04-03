import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents Encryption
 */
public class AESencrypt extends AmainAES implements IEncrypt {

    /**
     * Fields of Encryption
     */
    private byte[] PlainText;
    private byte[] m_Msg;

    /**
     * Constructor of AES algorithm to encryption
     *
     * @param plainText message path
     * @param out       output path
     * @param keyPath   keys path
     */
    public AESencrypt(String plainText, String out, String keyPath) {
        C = new ConvertByte();
        CipherKey1 = new byte[4][Nb];
        CipherKey2 = new byte[4][Nb];
        CipherKey3 = new byte[4][Nb];
        outPath = out;
        SplitToFields(keyPath, plainText);
    }

    /**
     * Splits byte arrays to keys and message fields of current class
     *
     * @param keyPath   keys path
     * @param plainText message path
     */
    @Override
    protected void SplitToFields(String keyPath, String plainText) {
        byte[] key;
        try {
            Path keyLocation = Paths.get(keyPath);
            key = Files.readAllBytes(keyLocation);

            Path msgLocation = Paths.get(plainText);
            m_Msg = Files.readAllBytes(msgLocation);
            PlainText = new byte[m_Msg.length];

            SeparateKeys(key);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to run the encryption
     */
    @Override
    public void Encrypt() {
        byte[][] state = new byte[4][Nb];
        byte[] currState = new byte[blockBytes];
        int startIndex = 0;
        while (startIndex < m_Msg.length) {
            InitialBlockMsg(currState, startIndex);
            C.Append(state, currState);

            ShiftRows(state);
            state = AddRoundKey(state, CipherKey1);

            ShiftRows(state);
            state = AddRoundKey(state, CipherKey2);

            ShiftRows(state);
            state = AddRoundKey(state, CipherKey3);

            UpdateCipher(startIndex, state);
            startIndex += 16;
        }
        WriteOutput(PlainText, outPath);
    }

    /**
     * Updates results of AES algorithm
     *
     * @param startIndex start index for update
     * @param state      current byte array
     */
    @Override
    public void UpdateCipher(int startIndex, byte[][] state) {
        byte[] cipher = new byte[blockBytes];
        C.Reduce(cipher, state);
        for (int i = 0; i < blockBytes; i++) {
            PlainText[startIndex + i] = cipher[i];
        }
    }

    /**
     * Updates current state from start index
     *
     * @param currState  current byte array
     * @param startIndex start index to update
     */
    @Override
    public void InitialBlockMsg(byte[] currState, int startIndex) {
        for (int i = 0; i < blockBytes; i++)
            currState[i] = m_Msg[startIndex + i];
    }

    /**
     * Shift left of Rows of matrix by steps
     *
     * @param state current byte array
     */
    @Override
    protected void ShiftRows(byte[][] state) {/* Shift left */
        byte[] tmp = new byte[4];
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                tmp[j] = state[i][(j + i) % Nb];
            for (int k = 0; k < 4; k++)
                state[i][k] = tmp[k];
        }
    }

}

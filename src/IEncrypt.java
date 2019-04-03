/**
 * This interface represents the methods of each encryption class
 */
public interface IEncrypt {

    void Encrypt();

    void UpdateCipher(int startIndex, byte[][] state);

    void InitialBlockMsg(byte[] currState, int startIndex);

}

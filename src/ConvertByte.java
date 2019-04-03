/**
 * This class represents conversion of a two-dimensional array into a one-dimensional array and vice versa
 */
public class ConvertByte {

    /**
     * Static field of the conversion
     */
    private static final int Nb = 4;

    /**
     * Conversion of a one-dimensional array into a two-dimensional array
     *
     * @param state output byte array
     * @param in    input byte array
     */
    public void Append(byte[][] state, byte[] in) {
        for (int i = 0, inIndex = 0; i < Nb; i++)
            for (int j = 0; j < 4; j++)
                state[j][i] = in[inIndex++];
    }

    /**
     * Conversion of a two-dimensional array into a one-dimensional array
     *
     * @param out   output byte array
     * @param state input byte array
     */
    public void Reduce(byte[] out, byte[][] state) {
        for (int i = 0, outIndex = 0; i < Nb; i++)
            for (int j = 0; j < 4; j++)
                out[outIndex++] = state[j][i];
    }
}

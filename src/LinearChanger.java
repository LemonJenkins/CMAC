/**
 * Created by Norfolk on 20.12.2016.
 */
public class LinearChanger {
    public static int polinom = 0x11d;
    public static char[] vec = {0x01, 0x01, 0x05, 0x01, 0x08, 0x06, 0x07, 0x04};


    public static char elementReduction(int inputElement) {
        if (!(inputElement > polinom || ((inputElement & 0x100) == 0x100))) {
            return (char) inputElement;
        }

        int highBit = 31;
        do {
            while (((inputElement >> highBit) & 0x1) == 0) {
                highBit--;
            }
            inputElement = inputElement ^ (polinom << highBit);
        } while (inputElement > polinom);
        return (char) inputElement;
    }

    public static void shiftCharArray(char[] vec) {
        char tmp = vec[0];
        for (int i = 0; i < vec.length - 1; i++) {
            vec[i] = vec[i + 1];
        }
        vec[vec.length - 1] = tmp;
    }

    public static int vectorMultiplication(char[] firstVec, char[] secondVec) {
        int result = 0;
        for (int i = 0; i < firstVec.length; i++) {
            result += firstVec[i] * secondVec[i];
        }
        return result;
    }

    public static char[] getMatrixColumn(char[][] matrix, int columnNumber) {
//        char[] result = new char[matrix.length];
//        for (int i = 0; i < matrix.length; i++) {
//            result[i] = matrix[i][columnNumber];
//        }
//        return result;
        return matrix[columnNumber];
    }

    public static char[][] linearTransformation(char[][] matrix) {
        char[][] resultMatrix = new char[matrix.length][matrix[0].length];
        char[] shftVec = vec.clone();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                resultMatrix[i][j] = elementReduction(vectorMultiplication(shftVec, getMatrixColumn(matrix, j)));
            }
            shiftCharArray(shftVec);
        }
        return resultMatrix;
    }
}

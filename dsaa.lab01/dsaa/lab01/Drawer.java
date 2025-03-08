package dsaa.lab01;

public class Drawer {
	private static void drawLine(int n, char ch) {
		for(int i=0; i<n; i++) {
			System.out.print(ch);
		}
	}


    public static void drawPyramid(int n) {
        int lineWidth = 2 * n - 1;

        for (int i = 0; i < n; i++) {
            int numXs = 2*i + 1;
            int numSpaces = (lineWidth - numXs) / 2;

			drawLine(numSpaces, '.');
			drawLine(numXs, 'X');
			drawLine(numSpaces, '.');
			System.out.println();
        }
    }

	public static void drawChristmassTree(int n) {
		int lineWidth = 2 * n - 1;
		for(int i=0; i<n; i++) {
			for (int j = 0; j < i+1; j++) {
				int numXs = 2*j + 1;
				int numSpaces = (lineWidth - numXs) / 2;

				drawLine(numSpaces, '.');
				drawLine(numXs, 'X');
				drawLine(numSpaces, '.');
				System.out.println();
			}
		}
	}
}

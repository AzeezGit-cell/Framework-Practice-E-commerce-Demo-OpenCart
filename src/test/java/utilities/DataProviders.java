package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "LoginData", parallel = false)
    public Object[][] getLoginData() throws Exception {

        String path = System.getProperty("user.dir") + "/testData/Open_cart_Login_credentials.xlsx";

        ExcelUtility xl = new ExcelUtility(path, "Sheet1");

        int rows = xl.getRowCount();
        int cols = xl.getCellCount(1);

        Object data[][] = new Object[rows][cols];

        for (int i = 1; i <= rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = xl.getCellData(i, j);
            }
        }

        xl.close();

        return data;
    }
}
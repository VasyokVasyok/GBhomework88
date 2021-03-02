import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface {
    private final Controller controller = new Controller();

    public void runApplication() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите название города на английском языке:");
            String city = scanner.nextLine();
            setGlobalCity(city);
            System.out.println("Введите ответ: 1 - Получить погоду на следующие 5 дней, " +
                    "выход (exit) - завершить работу");
            String result = scanner.nextLine();
            checkIsExit(result);
            try {
                validateUserInput(result);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            try {
                notifyController(result);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkIsExit(String result) {
        if (result.toLowerCase().equals("выход") || result.toLowerCase().equals("exit")) {
            System.out.println("Работа завершена");
            System.exit(0);
        }
    }

    private void setGlobalCity(String city) {
        ApplicationGlobalState.getInstance().setSelectedCity(city);
    }

    private void validateUserInput(String userInput) throws IOException {
        if (userInput == null || userInput.length() != 1) {
            throw new IOException("Введено некорректное значение: " + userInput);
        }
        int answer = 0;
        try {
            answer = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            throw new IOException("Введеный символ не является числом!");
        }
    }

    private void notifyController(String input) throws IOException, SQLException {
        controller.onUserInput(input);
    }
}

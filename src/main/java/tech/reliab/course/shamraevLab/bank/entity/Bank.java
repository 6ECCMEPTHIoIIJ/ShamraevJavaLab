package tech.reliab.course.shamraevLab.bank.entity;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;

import java.util.Random;

@Data
@ToString
public class Bank {
    private int id;
    private String name;
    private int rating;
    private double totalMoney;
    private double interestRate;

    @Min(value = 0, message = "The number of offices must be greater than or equal to 0")
    private int officeCount;
    @Min(value = 0, message = "The number of ATMs must be greater than or equal to 0")
    private int atmCount;
    @Min(value = 0, message = "The number of employees must be greater than or equal to 0")
    private int employeeCount;
    @Min(value = 0, message = "The number of clients must be greater than or equal to 0")
    private int clientCount;

    public Bank(int id, String name, int rating, double totalMoney, double interestRate) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.totalMoney = totalMoney;
        this.interestRate = interestRate;
    }

    public static class BankBuilder {
        private static final int MAX_RATING = 100;
        private static final int MAX_MONEY = 1000000;
        private static final double MAX_INTEREST_RATE = 20.0;
        private static final double BASE_INTEREST_RATE = 5.0;

        private static final Random random = new Random();

        private String name;

        public Bank createBank() {
            var id = random.nextInt();
            var rating = random.nextInt(MAX_RATING + 1);
            var totalMoney = random.nextInt(MAX_MONEY + 1);
            var interestRate = getInterestRate(rating);
            return new Bank(id, name, rating, totalMoney, interestRate);
        }

        public BankBuilder setName(String name) {
            this.name = name;
            return this;
        }

        private static double getInterestRate(int rating) {
            return BASE_INTEREST_RATE + (MAX_INTEREST_RATE - BASE_INTEREST_RATE) * (MAX_RATING - rating) / MAX_RATING;
        }
    }
}

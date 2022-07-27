package ir.ac.kntu;

import ir.ac.kntu.dao.MatchDAO;
import ir.ac.kntu.dao.UserDAO;
import ir.ac.kntu.matchandhischildren.Match;
import ir.ac.kntu.matchandhischildren.NormalMatch;
import ir.ac.kntu.userAndHisChildren.Admin;
import ir.ac.kntu.userAndHisChildren.Customer;
import ir.ac.kntu.userAndHisChildren.User;
import ir.ac.kntu.userInterface.Start;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
//        User firstAdmin = new Admin("mojtaba", "moj", "m.hadei.1381@gmail.com", "1", "0025366408", "09035873180");
//        User firstCustomer = new Customer("mostafa", "mos", "m.hadei.1382@gmail.com", "12", "56545321", "09376073956");
//        Match match = new NormalMatch("normalMatch1", LocalDateTime.now(), LocalDateTime.of(0,2,1,0,0));
//        new MatchDAO().updateMatch(match);
//        new UserDAO().updateUser(firstAdmin);
//        new UserDAO().updateUser(firstCustomer);
        new Start().startProgram();
    }


}
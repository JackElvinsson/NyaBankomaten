package com.example.nyabankomaten;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Client client;
    Account account;
    Interest loantype;
    Lists l = new Lists();

    Client c1 = new Client("Kalle", "123455677");
    Client c2 = new Client("Björne", "987456423");

//    List<Client> listOfClients = List.of(c1, c2);
    List<Client> listOfClients = new ArrayList<>();
//    List<String> listOfAllAccounts = new ArrayList<>();
     ObservableList<String> listOfAllAccounts = FXCollections.observableArrayList();
    List<Interest> listOfLoanTypes = List.of(Interest.BUSINESS, Interest.HOUSE, Interest.EDUCATION, Interest.PERSONAL, Interest.VEHICLE);

    @FXML
    private GridPane gridpane1;
    @FXML
    private Button addNewClient;
    @FXML
    private Label clientList;
    @FXML
    private Label accounts;
    @FXML
    private Label accountName;
    @FXML
    private TextField loanAmountField;
    @FXML
    private Button addClientButton;
    @FXML
    private Button createNewAccountButton;
    @FXML
    private Button removeClientButton;
    @FXML
    private Button newLoanButton;
    @FXML
    private Button payoffLoanButton;
    @FXML
    private Button deposit;
    @FXML
    private Button withdraw;
    @FXML
    private Button cancelButton;
    @FXML
    private Button applyForLoanButton;
    @FXML
    private ScrollPane accountHistory;
    @FXML
    private TextField addClientName;
    @FXML
    private TextField addClientSSC;
    @FXML
    private TextField depositAmount;
    @FXML
    private TextField withdrawAmount;

    @FXML
    private ListView<String> listViewClient;

    @FXML
    private ListView<String> listViewAccounts;
    @FXML
    private ListView<String> currentLoans;
    @FXML
    private ComboBox<String> loanTypeBox;
    @FXML
    private TextArea historyText;

    @FXML
    protected void onCancelButtonClick() {
        gridpane1.setVisible(false);
        currentLoans.getItems().clear();
        currentLoans.getItems().add(account.getLoanDetails().type +" - " + account.getLoan() + " " + account.getLoanDetails().interest);
    }

    @FXML
    protected void onNewLoanButtonClick() {
        gridpane1.setVisible(true);
        currentLoans.getItems().clear();
        currentLoans.getItems().add(account.getLoanDetails().type +" - " + account.getLoan() + " " + account.getLoanDetails().interest);
    }
    @FXML
    protected void onPayOffClick(){
        if(account.getBalance() >= account.getLoan()){
            account.accountHistory.add("Paid off "+ account.getLoanDetails() + " loan -"+ account.getLoan() + "kr - " + LocalDateTime.now().format(formatter));
            account.payLoan();
            listViewAccounts.getItems().clear();
            for (Account ac : client.getAccount()) {
                listViewAccounts.getItems().add(ac.getAccountNumber() + "\t\t\t|\t" + ac.getBalance());
            }
            historyText.clear();
            for (String str : account.accountHistory) {
                historyText.setText(historyText.getText() + str + "\n");
            }
            currentLoans.getItems().clear();
            currentLoans.getItems().add(account.getLoanDetails().type +" - " + account.getLoan() + " " + account.getLoanDetails().interest);

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Not enough funds in account");
            alert.show();
        }
    }

    @FXML
    protected void onApplyButtonClick() {
        if (account.getLoan() == 0) {
            loantype = listOfLoanTypes.get(loanTypeBox.getSelectionModel().getSelectedIndex());
            long loanAmount = Long.parseLong(loanAmountField.getText().trim());
            account.newLoan(loanAmount);
            account.setLoanDetails(loantype);
            account.accountHistory.add(loantype.type + " loan "+ loanAmount + "kr - " + LocalDateTime.now().format(formatter));
            account.setBalance(loanAmount);
            listViewAccounts.getItems().clear();
            for (Account ac : client.getAccount()) {
                listViewAccounts.getItems().add(ac.getAccountNumber() + "\t\t\t|\t" + ac.getBalance());
            }
            historyText.clear();
            for (String str : account.accountHistory) {
                historyText.setText(historyText.getText() + str + "\n");
            }
            gridpane1.setVisible(false);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Account already has a loan");
            alert.show();
        }
    }


    @FXML
    protected void onAddClientClick() {
        boolean b = true;
        for (Client listOfClient : listOfClients) {
            if (listOfClient.getPersonNumber().trim().equals(addClientSSC.getText().trim())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Client already exists");
                alert.show();
                b = false;
            }
        }
        if(b){
            if (addClientName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Please type in your name");
                alert.show();
            } else if (addClientSSC.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Please type in your SSC (social security number)");
                alert.show();
            } else {
                Client client = new AddClient(addClientName.getText(), addClientSSC.getText());
                listOfClients.add(client);
                System.out.println(listOfClients.size());
                System.out.println(listOfClients.get(listOfClients.size() - 1).getName());

                //TODO LÖS FELET
                try {
                    listViewClient.getItems().clear();
                    for (Client c : listOfClients) {
                        listViewClient.getItems().add(c.getName() + "\t\t\t|\t" + c.getPersonNumber());
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            addClientName.setText("");
            addClientSSC.setText("");
        }
    }

    @FXML
    protected void onDepositClick() {
        try {
            account.deposit(Integer.parseInt(depositAmount.getText()));
            account.accountHistory.add("Deposit - " + depositAmount.getText() + "kr - " + LocalDateTime.now().format(formatter));

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No account selected");
            alert.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No accounts available");
            alert.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Value has to be a number");
            alert.show();
        }
        listViewAccounts.getItems().clear();
        for (Account ac : client.getAccount()) {
            listViewAccounts.getItems().add(ac.getAccountNumber() + "\t\t\t|\t" + ac.getBalance());
        }
        depositAmount.setText("");
        historyText.clear();
        for (String str : account.accountHistory) {
            historyText.setText(historyText.getText() + str + "\n");
        }
    }

    @FXML
    protected void onWithdrawClick() {
        try {
            client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex()).withdraw(Integer.parseInt(withdrawAmount.getText()));
            client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex()).accountHistory.add("Withdrawal - " + withdrawAmount.getText() + "kr - " + LocalDateTime.now() + "\n");
        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No account selected");
            alert.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No accounts available");
            alert.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Value has to be a number");
            alert.show();
        }
        listViewAccounts.getItems().clear();
        for (Account ac : client.getAccount()) {
            listViewAccounts.getItems().add(ac.getAccountNumber() + "\t\t\t|\t" + ac.getBalance());
        }
        withdrawAmount.setText("");
        historyText.clear();
        for (String str : account.accountHistory) {
            historyText.setText(historyText.getText() + str + "\n");
        }
    }

    @FXML
    protected void onCreateNewAccountClick() {
        Account a = new Account(l.getNewAccountNumber(listOfAllAccounts));

        try {
            client.addAccount(a);

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No client selected");
            alert.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No clients available");
            alert.show();
        }

        //TODO LÖS FELET
        try {
            listViewAccounts.getItems().clear();
            for (Account ac : client.getAccount()) {
                listViewAccounts.getItems().add(ac.getAccountNumber() + "\t\t\t|\t" + ac.getBalance());
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        currentLoans.getItems().clear();
        currentLoans.getItems().add(account.getLoanDetails().type +" - " + account.getLoan() + " " + account.getLoanDetails().interest);
    }

    @FXML
    protected void onRemoveClientClick() {

        try {
            listOfClients.remove(listViewClient.getSelectionModel().getSelectedIndex());
            listViewClient.getItems().remove(listViewClient.getSelectionModel().getSelectedIndex());

        } catch (IndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No client selected");
            alert.show();
//        } catch (NullPointerException e) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText("No clients available");
//            alert.show();
        }
        //TODO LÖS FELET
        try {
            listViewClient.getItems().clear();
            for (Client c : listOfClients) {
                listViewClient.getItems().add(c.getName() + "\t\t\t|\t" + c.getPersonNumber());
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        currentLoans.getItems().clear();
        currentLoans.getItems().add(account.getLoanDetails().type +" - " + account.getLoan() + " " + account.getLoanDetails().interest);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        for (Interest i : listOfLoanTypes) {
            loanTypeBox.getItems().add(i.type);
        }
//        for (Client c : listOfClients) {
//            listViewClient.getItems().add(c.getName() + "\t\t\t|\t" + c.getPersonNumber());
//        }
        listViewClient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                client = listOfClients.get(listViewClient.getSelectionModel().getSelectedIndex());

                //TODO LÖS FELET
                try {
                    listViewAccounts.getItems().clear();
                    for (Account a : client.getAccount()) {
                        listViewAccounts.getItems().add(a.getAccountNumber() + "\t\t\t|\t" + a.getBalance());
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
                historyText.clear();
                currentLoans.getItems().clear();
            }
        });
        listViewAccounts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                account = client.getAccount().get(listViewAccounts.getSelectionModel().getSelectedIndex());
                historyText.clear();
                for (String str : account.accountHistory) {
                    historyText.setText(historyText.getText() + str + "\n");
                }
                currentLoans.getItems().clear();
                currentLoans.getItems().add(account.getLoanDetails().type +" - " + account.getLoan() + " " + account.getLoanDetails().interest);
            }
        });
//        listOfAllAccounts.addListener(new ListChangeListener<String>() {
//            @Override
//            public void onChanged(Change<? extends String> change) {
//                listViewAccounts.getItems().clear();
//                System.out.println("listener");
//                for (Account a : client.getAccount()) {
//                    listViewAccounts.getItems().add(a.getAccountNumber() + "\t\t\t|\t" + a.getBalance());
//                }
//            }
//        });
    }
}

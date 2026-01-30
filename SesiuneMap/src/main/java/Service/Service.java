package Service;

import Domain.*;
import Observer.Observable;
import Observer.Observer;
import Repo.RepoAdmin;
import Repo.RepoDelear;
import Repo.RepoMasina;

import java.util.ArrayList;
import java.util.List;

public class Service implements Observable {
    private RepoAdmin repoAdmin;
    private RepoDelear repoDelear;
    private RepoMasina repoMasina;

    private List<Observer> observers=new ArrayList<>();

    public Service(RepoAdmin repoAdmin, RepoDelear repoDelear,RepoMasina repoMasina) {
        this.repoAdmin = repoAdmin;
        this.repoDelear = repoDelear;
        this.repoMasina = repoMasina;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public List<Admin> getAdminsService() {
        return repoAdmin.getAdmins();
    }

    public List<Dealer> getDealersService() {
        return repoDelear.getDealers();
    }

    public User login(String username, String password) {
        for (Admin admin : repoAdmin.getAdmins()) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        for (Dealer dealer : repoDelear.getDealers()) {
            if (dealer.getUsername().equals(username) && dealer.getPassword().equals(password)) {
                return dealer;
            }
        }
        return null;
    }

    public List<Masina> getMasiniService() {
        return repoMasina.getMasini();
    }

    public List<Masina> getMasiniForApprovalService() {
        return repoMasina.getMasiniForApproval();
    }

    public void updateStatusService(int idMasina, Status status) {
        repoMasina.updateStatus(idMasina, status);
        notifyObservers();
    }
}

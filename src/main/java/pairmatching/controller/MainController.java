package pairmatching.controller;

import pairmatching.domain.Course;
import pairmatching.domain.Crews;
import pairmatching.view.Input;
import pairmatching.view.Output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainController {
    Crews pairmatching;
    HashMap<String, List<String>> pairInfo;

    public MainController() {
        pairmatching = new Crews();
        pairInfo = new HashMap<>();
        CrewInformationController.readCrews(pairmatching);
    }

    public void playGame() {
        while (true) {
            int inputChoice = chooseFunction();
            if (inputChoice == 0) {
                return;
            }
            if (inputChoice == 1) {
                pairStart();
            }
        }
    }

    private void pairStart() {
        Output.printMissionAndProcess();
        String inputProcess = Input.inputProcess();
        if (pairInfo.containsKey(inputProcess)) {
            System.out.println("매칭 정보가 있습니다. 다시 매칭하시겠습니까?");
            System.out.println("네 | 아니오");
            String inputData = Input.InputYesOrNo();
            selectMatchingRestart(inputData, inputProcess);
        } else if (!pairInfo.containsKey(inputProcess)) {
            setPair(inputProcess);
        }
        System.out.println("\n페어 매칭 결과입니다.");
        printMatchingList(pairInfo.get(inputProcess));
        System.out.println();
    }

    private void selectMatchingRestart(String inputData, String inputProcess) {
        if (inputData.equals("아니오")){
            return;
        }
        setPair(inputProcess);
    }

    private void setPair(String inputProcess) {
        String[] inputSplit = inputProcess.split(", ");
        System.out.println(inputSplit[0]);
        if (inputSplit[0].equals(Course.BACKEND.getName())) {
            pairInfo.put(inputProcess, PairmatchingController.pairmatchingStart(pairmatching.getCrewBackendNames()));
            return;
        }
        pairInfo.put(inputProcess, PairmatchingController.pairmatchingStart(pairmatching.getCrewFrontendNames()));
    }

    private int chooseFunction() {
        while (true) {
            Output.printFunctionSelection();
            String functionSelection = Input.inputFunctionSelection();
            try {
                if (ValidationController.checkSpellQValidation(functionSelection)) {
                    return 0;
                }
                ValidationController.chooseFunctionValidation(functionSelection);
                return Integer.parseInt(functionSelection);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printMatchingList(List<String> matchingList) {
        if (matchingList.size() % 2 == 0) {
            printEven(matchingList);
            return;
        }
        printOdd(matchingList);
    }

    private void printOdd(List<String> matchingList) {
        List<String> matchings = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < matchingList.size() - 3; i++) {
            matchings.add(matchingList.get(i));
            count += 1;
            if (count == 2) {
                count = 0;
                System.out.println(String.join(" : ", matchings));
                matchings = new ArrayList<>();
            }
        }
        for (int i = matchingList.size() - 3; i < matchingList.size(); i++) {
            matchings.add(matchingList.get(i));
        }
        System.out.println(String.join(" : ", matchings));
    }

    private void printEven(List<String> matchingList) {
        List<String> matchings = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < matchingList.size(); i++) {
            matchings.add(matchingList.get(i));
            count += 1;
            if (count == 2) {
                count = 0;
                System.out.println(String.join(" : ", matchings));
                matchings = new ArrayList<>();
            }
        }
    }
}

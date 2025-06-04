package dev.qf.client;

import common.network.packet.HandShakeC2SInfo;
import common.registry.RegistryManager;
import dev.qf.client.network.KioskNettyClient;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static KioskNettyClient INSTANCE = new KioskNettyClient();

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        INSTANCE.run();
        System.out.println("네트워크 연결 대기");

        int connectionAttempts = 0;
        while (!INSTANCE.isConnected() && connectionAttempts < 100) {
            Thread.sleep(100);
            connectionAttempts++;
        }

        if (!INSTANCE.isConnected()) {
            System.err.println("서버에 연결할 수 없습니다.");
            JOptionPane.showMessageDialog(null,
                    "서버를 먼저 실행해주세요.",
                    "연결 오류",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        System.out.println("서버에 연결됨. HandShake 전송...");
        INSTANCE.sendSerializable(new HandShakeC2SInfo("test"));

        // Registry 데이터 대기 (타임아웃 추가)
        System.out.println("서버 데이터 대기 중...");
        int dataWaitAttempts = 0;
        while (RegistryManager.CATEGORIES.size() == 0 && dataWaitAttempts < 50) {
            Thread.sleep(200);
            dataWaitAttempts++;
            if (dataWaitAttempts % 10 == 0) {
                System.out.println("데이터 대기 중... (" + dataWaitAttempts + "/50)");
            }
        }

        if (RegistryManager.CATEGORIES.size() == 0) {
            System.err.println("서버로부터 데이터를 받지 못했습니다.");
            JOptionPane.showMessageDialog(null,
                    "서버로부터 데이터를 받지 못했습니다.",
                    "데이터 오류",
                    JOptionPane.ERROR_MESSAGE);

            // 데이터가 없어도 UI는 열어주기
            System.out.println("데이터 없이 UI 실행...");
        } else {
            System.out.println("데이터 로드 완료. 카테고리 수: " + RegistryManager.CATEGORIES.size());
        }

        // UI 선택 대화상자
        SwingUtilities.invokeAndWait(() -> {
            String[] options = {"카테고리 관리", "메뉴 관리", "종료"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "어떤 관리 화면을 열까요?",
                    "관리 시스템 선택",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0: // 카테고리 관리
                    new CategoryManagementUI().setVisible(true);
                    break;
                case 1: // 메뉴 관리
                    new MenuManagementUI().setVisible(true);
                    break;
                case 2: // 종료
                default:
                    System.exit(0);
                    break;
            }
        });
    }
}

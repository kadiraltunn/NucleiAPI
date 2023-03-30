package com.example.demmooo.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NucleiScan {

    public void executeCommand(String target) throws InterruptedException, IOException {
        Process p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "plink kali@192.168.1.101 -pw kali -no-antispoof " +
                "\"nuclei -u " + target + " -json -o /home/kali/Results1.txt\""});
        p.waitFor();
    }
}

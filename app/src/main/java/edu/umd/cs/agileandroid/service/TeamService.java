package edu.umd.cs.agileandroid.service;


import java.util.List;

public interface TeamService {
    List<String> getDefinitionOfDone(String teamId);
    String getTeamReminder(String teamId);
}

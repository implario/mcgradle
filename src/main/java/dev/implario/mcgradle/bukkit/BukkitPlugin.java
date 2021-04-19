package dev.implario.mcgradle.bukkit;

import lombok.Data;
import org.gradle.internal.impldep.com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Data
public class BukkitPlugin {

    private String main;
    private String author;
    private String name;
    private String version = "1.0";
    private List<String> loadbefore = new ArrayList<>();
    private List<String> depend = new ArrayList<>();
    private List<String> softdepend = new ArrayList<>();

    public String toYml() {

        if (main == null) throw new NoSuchElementException("bukkitPlugin { main } not specified");

        if (name == null) name = main;

        return "name: " + name + '\n' +
                "version: " + version + '\n' +
                "main: " + main + '\n' +
                "author: " + author + '\n' +
                "loadbefore: [" + String.join(", ", loadbefore) + "]" + '\n' +
                "softdepend: [" + String.join(", ", softdepend) + "]" + '\n' +
                "depend: [" + String.join(", ", depend) + "]" + '\n';

    }
}

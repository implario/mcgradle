package dev.implario.mcgradle;

import dev.implario.mcgradle.bukkit.BukkitPlugin;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.internal.impldep.com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

public class McgradlePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        BukkitPlugin plugin = project.getExtensions().create("bukkitPlugin", BukkitPlugin.class);

        File file = new File(project.getBuildDir(), "generated/mcgradle/plugin.yml");

        Task pluginYml = project.getTasks().create("pluginYml", t -> {
            t.doFirst(tt -> {
                try {
                    file.getParentFile().mkdirs();
                    ResourceGroovyMethods.setText(file, plugin.toYml());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            t.getOutputs().file(file);
        });

        project.afterEvaluate(p -> {
            Task jar = project.getTasks().getByName("jar");
            jar.dependsOn(pluginYml);
            jar.doFirst(t -> {
                ((Jar) t).from(file);
            });
        });




    }

}

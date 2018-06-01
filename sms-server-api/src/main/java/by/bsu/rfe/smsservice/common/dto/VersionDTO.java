package by.bsu.rfe.smsservice.common.dto;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pluhin on 11/27/16.
 */
public class VersionDTO {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionDTO.class);

    private String branch;                  // =${git.branch}
    private String describe;                // =${git.commit.id.describe}
    private String commitId;                // =${git.commit.id}
    private String commitIdAbbrev;          // =${git.commit.id.abbrev}
    private String buildUserName;           // =${git.build.user.name}
    private String buildUserEmail;          // =${git.build.user.email}
    private String buildTime;               // =${git.build.time}
    private String commitUserName;          // =${git.commit.user.name}
    private String commitUserEmail;         // =${git.commit.user.email}
    private String commitMessageFull;       // =${git.commit.message.full}
    private String commitMessageShort;      // =${git.commit.message.short}
    private String commitTime;              // =${git.commit.time}

    public VersionDTO() {
    }

    public static VersionDTO loadFromProperties(String resource) {
        try (InputStream is = VersionDTO.class.getClassLoader().getResourceAsStream(resource)) {
            Properties properties = new Properties();
            properties.load(is);

            VersionDTO state = new VersionDTO();

            state.branch = properties.getProperty("git.branch");
            state.describe = properties.getProperty("git.commit.id.describe");
            state.commitId = properties.getProperty("git.commit.id");
            state.commitIdAbbrev = properties.getProperty("git.commit.id.abbrev");
            state.buildUserName = properties.getProperty("git.build.user.name");
            state.buildUserEmail = properties.getProperty("git.build.user.email");
            state.buildTime = properties.getProperty("git.build.time");
            state.commitUserName = properties.getProperty("git.commit.user.name");
            state.commitUserEmail = properties.getProperty("git.commit.user.email");
            state.commitMessageFull = properties.getProperty("git.commit.message.full");
            state.commitMessageShort = properties.getProperty("git.commit.message.short");
            state.commitTime = properties.getProperty("git.commit.time");

            return state;
        } catch (Exception ex) {
            LOGGER.error("Error reading version info", ex);
        }
        return null;
    }

    public String getBranch() {
        return branch;
    }

    public String getDescribe() {
        return describe;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getCommitIdAbbrev() {
        return commitIdAbbrev;
    }

    public String getBuildUserName() {
        return buildUserName;
    }

    public String getBuildUserEmail() {
        return buildUserEmail;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public String getCommitUserName() {
        return commitUserName;
    }

    public String getCommitUserEmail() {
        return commitUserEmail;
    }

    public String getCommitMessageFull() {
        return commitMessageFull;
    }

    public String getCommitMessageShort() {
        return commitMessageShort;
    }

    public String getCommitTime() {
        return commitTime;
    }
}

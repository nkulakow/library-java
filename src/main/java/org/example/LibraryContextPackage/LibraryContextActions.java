package org.example.LibraryContextPackage;

import java.util.Collection;

public interface LibraryContextActions {
    public boolean askToJoinCollection(Admin admin);
    public boolean askToLeaveCollection(Admin admin);
    public String describe();
    public int hashCode();
    public boolean equals(Object obj);
    String[] getRepresentation();
}

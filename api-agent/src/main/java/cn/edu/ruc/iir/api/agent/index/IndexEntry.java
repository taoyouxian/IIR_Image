package cn.edu.ruc.iir.api.agent.index;

import java.util.Objects;

public class IndexEntry {
    private String indexPath;
    private String scanPath;

    public IndexEntry() {
    }

    public IndexEntry(String indexPath, String scanPath) {
        this.indexPath = indexPath;
        this.scanPath = scanPath;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public String getScanPath() {
        return scanPath;
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexEntry that = (IndexEntry) o;
        return Objects.equals(indexPath, that.indexPath) &&
                Objects.equals(scanPath, that.scanPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(indexPath, scanPath);
    }

    @Override
    public String toString() {
        return "IndexEntry{" +
                "indexPath='" + indexPath + '\'' +
                ", scanPath='" + scanPath + '\'' +
                '}';
    }
}

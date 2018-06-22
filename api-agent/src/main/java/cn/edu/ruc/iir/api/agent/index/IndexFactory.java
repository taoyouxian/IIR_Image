package cn.edu.ruc.iir.api.agent.index;

import java.util.HashMap;
import java.util.Map;

public class IndexFactory {
    private static IndexFactory instance = null;

    public static IndexFactory Instance() {
        if (instance == null) {
            instance = new IndexFactory();
        }
        return instance;
    }

    private Map<IndexEntry, FileSearch> indexCache = null;

    private Map<IndexEntry, TermSearch> termCache = null;

    private IndexFactory() {
        this.indexCache = new HashMap<>();
        this.termCache = new HashMap<>();
    }

    public void cacheIndex(IndexEntry name, FileSearch inverted) {
        this.indexCache.put(name, inverted);
    }

    public void cacheTermIndex(IndexEntry name, TermSearch inverted) {
        this.termCache.put(name, inverted);
    }

    public FileSearch getIndex(IndexEntry name) {
        return this.indexCache.get(name);
    }

    public TermSearch getTermIndex(IndexEntry name) {
        return this.termCache.get(name);
    }

}

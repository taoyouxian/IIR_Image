package cn.edu.ruc.iir.api.agent.service;

import cn.edu.ruc.iir.api.agent.domain.*;
import cn.edu.ruc.iir.api.agent.util.FirstLetterUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class StarServiceImpl implements StarServiceI {
    @Value("${hotPath}")
    private String hotPath;

    @Value("${Star.Data.Path}")
    private String dataPath;

    @Override
    public List<Prize> getPrize(String star_name) throws Exception {
        List<Prize> prizelist = new ArrayList<Prize>();
        String new_path = dataPath + star_name + "/personal_prize.txt";
        String line;
        String[] lines;
        BufferedReader bf = new BufferedReader(new FileReader(new_path));
        while ((line = bf.readLine()) != null) {
            lines = line.split("\t");
            Prize prizeInfo = new Prize();
            if (lines.length == 2) {
                prizeInfo = new Prize(lines[0], lines[1]);
            } else {
                prizeInfo = new Prize(lines[0], "");
            }
            prizelist.add(prizeInfo);
        }
        bf.close();
        return prizelist;
    }

    @Override
    public List<Experience> getExperience(String star_name) throws Exception {
        List<Experience> experienceList = new ArrayList<Experience>();
        String new_path = dataPath + star_name + "/personal_experience.txt";
        String line;
        BufferedReader bf = new BufferedReader(new FileReader(new_path));
        StringBuilder sb = new StringBuilder();
        while ((line = bf.readLine()) != null) {
            if (line.trim().length() > 0) {
                sb.append(line.trim());
            }
        }
        String[] experiences = sb.toString().split("<br>");
        for (String ex : experiences) {
            if (ex.length() > 0) {
                Experience experience = new Experience(ex);
                experienceList.add(experience);
            }
        }
        return experienceList;
    }

    @Override
    public Intro getIntro(String star_name) throws Exception {
        Intro introInfo = new Intro();
        String new_path = dataPath + star_name + "/personal_intro.txt";
        String line;
        String intro = new String();
        BufferedReader bf = new BufferedReader(new FileReader(new_path));
        while ((line = bf.readLine()) != null) {
            intro = intro + line + "\n";
        }
        introInfo.setContent(intro);
        bf.close();
        return introInfo;
    }

    @Override
    public List<Relation> getRelation(String star_name) throws Exception {
        List<Relation> rellist = new ArrayList<Relation>();
        String new_path = dataPath + star_name + "/personal_relation.txt";
        String line;
        String[] lines;
        BufferedReader bf = new BufferedReader(new FileReader(new_path));
        while ((line = bf.readLine()) != null) {
            lines = line.split("\t");
            Relation rel = null;
            if (lines.length == 2) {
                int s = lines[1].indexOf("（");
                if (s < 0) {
                    rel = new Relation(lines[0], lines[1], "");
                } else {
                    String name = lines[1].substring(0, s);
                    String career = lines[1].substring(s + 1, lines[1].length() - 1);
                    rel = new Relation(lines[0], name, career);
                }
            } else {
                rel = new Relation(lines[0], "", "");
            }
            rellist.add(rel);
        }
        bf.close();
        return rellist;
    }

    @Override
    public List<BasicInfo> getBasicinfo(String[] star_name_list) throws Exception {
        List<BasicInfo> binfolist = new ArrayList<BasicInfo>();
        for (String star_name : star_name_list) {
//			System.out.println(star_name);
            BasicInfo binfo = new BasicInfo();
            binfo.setStar_name(star_name);
            FirstLetterUtil firstLetterUtil = new FirstLetterUtil();
            String firstLetter = firstLetterUtil.getFirstLetter(star_name);
            String fistChar = firstLetter.substring(0, 1).toUpperCase();
            binfo.setFirst_letter(fistChar);
            String new_path = hotPath + star_name + "/personal_info.txt";
            System.out.println(new_path);
            String line;
            String[] lines;
            BufferedReader bf = new BufferedReader(new FileReader(new_path));
            while ((line = bf.readLine()) != null) {
                lines = line.split("\t");
                if (lines.length == 2) {
                    if (lines[0].equals("出 生")) {
                        binfo.setBirthdate(lines[1]);
                    }
                    if (lines[0].equals("血 型")) {
                        binfo.setBlood_type(lines[1]);
                    }
                    if (lines[0].equals("身 高")) {
                        binfo.setHeight(lines[1]);
                    }
                    if (lines[0].equals("体 重")) {
                        binfo.setWeight(lines[1]);
                    }
                    if (lines[0].equals("国 籍")) {
                        binfo.setNationality(lines[1]);
                    }
                    if (lines[0].equals("星 座")) {
                        binfo.setConstellation(lines[1]);
                    }
                    if (lines[0].equals("别 名")) {
                        binfo.setNick_name(lines[1]);
                    }
                }
            }
            binfolist.add(binfo);
            bf.close();
        }
        System.out.println(binfolist.toString());
        return binfolist;
    }

    @Override
    public BasicInfo getBasicinfo(String star_name) throws Exception {
        BasicInfo binfo = new BasicInfo();
        binfo.setStar_name(star_name);
        String new_path = dataPath + star_name + "/personal_info.txt";
        String line;
        String[] lines;
        BufferedReader bf = new BufferedReader(new FileReader(new_path));
        while ((line = bf.readLine()) != null) {
            lines = line.split("\t");
            if (lines.length == 2) {
                if (lines[0].equals("出 生")) {
                    binfo.setBirthdate(lines[1]);
                }
                if (lines[0].equals("血 型")) {
                    binfo.setBlood_type(lines[1]);
                }
                if (lines[0].equals("身 高")) {
                    binfo.setHeight(lines[1]);
                }
                if (lines[0].equals("体 重")) {
                    binfo.setWeight(lines[1]);
                }
                if (lines[0].equals("国 籍")) {
                    binfo.setNationality(lines[1]);
                }
                if (lines[0].equals("星 座")) {
                    binfo.setConstellation(lines[1]);
                }
                if (lines[0].equals("别 名")) {
                    binfo.setNick_name(lines[1]);
                }
            }
        }
        bf.close();
        return binfo;
    }

    @Override
    public BasicInfo getBasicinfo(String star_name, String new_path) throws Exception {
        BasicInfo binfo = new BasicInfo();
        binfo.setStar_name(star_name);
        String line;
        String[] lines;
        BufferedReader bf = new BufferedReader(new FileReader(new_path));
        while ((line = bf.readLine()) != null) {
            lines = line.split("\t");
            if (lines.length == 2) {
                if (lines[0].equals("首字母")) {
                    binfo.setFirst_letter(lines[1]);
                }
                if (lines[0].equals("出 生")) {
                    binfo.setBirthdate(lines[1]);
                }
                if (lines[0].equals("血 型")) {
                    binfo.setBlood_type(lines[1]);
                }
                if (lines[0].equals("身 高")) {
                    binfo.setHeight(lines[1]);
                }
                if (lines[0].equals("体 重")) {
                    binfo.setWeight(lines[1]);
                }
                if (lines[0].equals("国 籍")) {
                    binfo.setNationality(lines[1]);
                }
                if (lines[0].equals("星 座")) {
                    binfo.setConstellation(lines[1]);
                }
                if (lines[0].equals("别 名")) {
                    binfo.setNick_name(lines[1]);
                }
            }
        }
        bf.close();
        return binfo;
    }

    @Override
    public List<HotStar> getHotStar() throws Exception {
        List<HotStar> hotList = new ArrayList<HotStar>();
        String line;
        String[] lines;
        BufferedReader bf = new BufferedReader(new FileReader(hotPath));
        while ((line = bf.readLine()) != null) {
            lines = line.split(",");
            if (lines.length == 4) {
                HotStar star = new HotStar(lines[0], lines[1], lines[2], lines[3]);
                hotList.add(star);
            }
        }
        Collections.sort(hotList);
        return hotList;
    }

    @Override
    public List<Work> getTV(String star_name) throws Exception {
        List<Work> tvList = new ArrayList<Work>();
        String new_path = dataPath + star_name + "/personal_tv.txt";
//        String new_path = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Data/杨洋/personal_tv.txt";
        String line;
        BufferedReader filereader = new BufferedReader(new FileReader(new_path));
        while ((line = filereader.readLine()) != null) {
            Work film = new Work();
            String name = null;
            String charaterStr = null;
            String directorStr = null;
            String performerStr = null;
//            List<String> directorList = new ArrayList<>();
//            List<String> performerList = new ArrayList<>();
            int charaterIndex = line.indexOf("饰演");
            int directorIndex = line.indexOf("导演");
            int performerIndex = line.lastIndexOf("主演");
            if (charaterIndex < 0 && directorIndex < 0 && performerIndex < 0) {
                name = line.substring(0);
                System.out.println("000");
            } else if (charaterIndex < 0 && directorIndex < 0 && performerIndex > 0) {
                name = line.substring(0, performerIndex);
                performerStr = line.substring(performerIndex);
            } else if (charaterIndex < 0 && directorIndex > 0 && performerIndex < 0) {
                name = line.substring(0, directorIndex);
                directorStr = line.substring(directorIndex);
            } else if (charaterIndex < 0 && directorIndex > 0 && performerIndex > 0) {
                name = line.substring(0, directorIndex);
                directorStr = line.substring(directorIndex, performerIndex);
                performerStr = line.substring(performerIndex);
            } else if (charaterIndex > 0 && directorIndex < 0 && performerIndex < 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex);
            } else if (charaterIndex > 0 && directorIndex < 0 && performerIndex > 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, performerIndex);
                performerStr = line.substring(charaterIndex, performerIndex);
            } else if (charaterIndex > 0 && directorIndex > 0 && performerIndex < 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, directorIndex);
                directorStr = line.substring(directorIndex);
            } else if (charaterIndex > 0 && directorIndex > 0 && performerIndex > 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, directorIndex);
                directorStr = line.substring(directorIndex, performerIndex);
                performerStr = line.substring(performerIndex);
            } else {
                System.out.println("1000");
            }

            String charaters = "", directors = "", performers = "";
            String[] splits;
            if (name != null) {
                name = name.replace("\t", "").trim();
            }
            if (charaterStr != null) {
                charaterStr = charaterStr.replace("\t", " ");
                splits = charaterStr.trim().split(":");
                if (splits.length > 1)
                    charaters = splits[1];
            }
            if (directorStr != null) {
                directorStr = directorStr.replace("\t", " ");
                splits = directorStr.trim().split(":");
                if (splits.length > 1)
                    directors = splits[1];
            }
            if (performerStr != null) {
                performerStr = performerStr.replace("\t", " ");
                splits = performerStr.trim().split(":");
                if (splits.length > 1)
                    performers = splits[1];
            }

            film.setName(name);
            film.setCharater(charaters);
            film.setDirector(directors);
            film.setPerformer(performers);
            tvList.add(film);
        }
        filereader.close();
        return tvList;
    }


    @Override
    public Movie getContent(String star_name, String new_path) throws IOException {
        String line;
        BufferedReader filereader = new BufferedReader(new FileReader(new_path));
        Movie movie = new Movie();
        List<Content> film = new ArrayList<>();
        int i = 0;
        while ((line = filereader.readLine()) != null) {
            String l = line.trim();
            if (i == 0) {
                int index = l.indexOf("电影名");
                if (index >= 0) {
                    String[] split = l.split("：");
                    if (split.length > 1) {
                        String name = split[1].replace("\t", "");
                        movie.setName(name);
                    }
                }
            } else {

            }
            if (l.length() > 0) {
                Content content = new Content(l);
                film.add(content);
            }
            i++;
        }
        movie.setContent(film);
        filereader.close();
        return movie;
    }

    @Override
    public List<Work> getFilm(String star_name) throws Exception {
        List<Work> filmList = new ArrayList<Work>();
        String new_path = dataPath + star_name + "/personal_movie.txt";
//        String new_path = "/home/tao/software/opt/tomcat-8.5.31/webapps/ROOT/ImageRetrieval/Data/杨洋/personal_movie.txt";
        String line;
        BufferedReader filereader = new BufferedReader(new FileReader(new_path));
        while ((line = filereader.readLine()) != null) {
            Work film = new Work();
            String name = null;
            String charaterStr = null;
            String directorStr = null;
            String performerStr = null;
//            List<String> directorList = new ArrayList<>();
//            List<String> performerList = new ArrayList<>();
            int charaterIndex = line.indexOf("饰演");
            int directorIndex = line.indexOf("导演");
            int performerIndex = line.lastIndexOf("主演");
            if (charaterIndex < 0 && directorIndex < 0 && performerIndex < 0) {
                name = line.substring(0);
                System.out.println("000");
            } else if (charaterIndex < 0 && directorIndex < 0 && performerIndex > 0) {
                name = line.substring(0, performerIndex);
                performerStr = line.substring(performerIndex);
            } else if (charaterIndex < 0 && directorIndex > 0 && performerIndex < 0) {
                name = line.substring(0, directorIndex);
                directorStr = line.substring(directorIndex);
            } else if (charaterIndex < 0 && directorIndex > 0 && performerIndex > 0) {
                name = line.substring(0, directorIndex);
                directorStr = line.substring(directorIndex, performerIndex);
                performerStr = line.substring(performerIndex);
            } else if (charaterIndex > 0 && directorIndex < 0 && performerIndex < 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex);
            } else if (charaterIndex > 0 && directorIndex < 0 && performerIndex > 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, performerIndex);
                performerStr = line.substring(charaterIndex, performerIndex);
            } else if (charaterIndex > 0 && directorIndex > 0 && performerIndex < 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, directorIndex);
                directorStr = line.substring(directorIndex);
            } else if (charaterIndex > 0 && directorIndex > 0 && performerIndex > 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, directorIndex);
                directorStr = line.substring(directorIndex, performerIndex);
                performerStr = line.substring(performerIndex);
            } else {
                System.out.println("1000");
            }

            String charaters = "", directors = "", performers = "";
            String[] splits;
            if (name != null) {
                name = name.replace("\t", "").trim();
            }
            if (charaterStr != null) {
                charaterStr = charaterStr.replace("\t", " ");
                splits = charaterStr.trim().split(":");
                if (splits.length > 1)
                    charaters = splits[1];
            }
            if (directorStr != null) {
                directorStr = directorStr.replace("\t", " ");
                splits = directorStr.trim().split(":");
                if (splits.length > 1)
                    directors = splits[1];
            }
            if (performerStr != null) {
                performerStr = performerStr.replace("\t", " ");
                splits = performerStr.trim().split(":");
                if (splits.length > 1)
                    performers = splits[1];
            }

            film.setName(name);
            film.setCharater(charaters);
            film.setDirector(directors);
            film.setPerformer(performers);
            filmList.add(film);
        }
        filereader.close();
        return filmList;
    }


    @Override
    public List<Work> getFilmByPath(String new_path) throws Exception {
        List<Work> filmList = new ArrayList<Work>();
        String line;
        BufferedReader filereader = new BufferedReader(new FileReader(new_path));
        while ((line = filereader.readLine()) != null) {
            Work film = new Work();
            String name = "";
            String charaterStr = null;
            String directorStr = null;
            String performerStr = null;
            int charaterIndex = line.indexOf("饰演");
            int directorIndex = line.indexOf("导演");
            int performerIndex = line.lastIndexOf("主演");
            if (charaterIndex < 0 && directorIndex < 0 && performerIndex < 0) {
                name = line.substring(0);
                System.out.println("000");
            } else if (charaterIndex < 0 && directorIndex < 0 && performerIndex > 0) {
                name = line.substring(0, performerIndex);
                performerStr = line.substring(performerIndex);
            } else if (charaterIndex < 0 && directorIndex > 0 && performerIndex < 0) {
                name = line.substring(0, directorIndex);
                directorStr = line.substring(directorIndex);
            } else if (charaterIndex < 0 && directorIndex > 0 && performerIndex > 0) {
                name = line.substring(0, directorIndex);
                directorStr = line.substring(directorIndex, performerIndex);
                performerStr = line.substring(performerIndex);
            } else if (charaterIndex > 0 && directorIndex < 0 && performerIndex < 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex);
            } else if (charaterIndex > 0 && directorIndex < 0 && performerIndex > 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, performerIndex);
                performerStr = line.substring(charaterIndex, performerIndex);
            } else if (charaterIndex > 0 && directorIndex > 0 && performerIndex < 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, directorIndex);
                directorStr = line.substring(directorIndex);
            } else if (charaterIndex > 0 && directorIndex > 0 && performerIndex > 0) {
                name = line.substring(0, charaterIndex);
                charaterStr = line.substring(charaterIndex, directorIndex);
                System.out.println(name + "," + charaterStr);
                directorStr = line.substring(directorIndex, performerIndex);
                performerStr = line.substring(performerIndex);
            } else {
                System.out.println("1000");
            }

            String charaters = "", directors = "", performers = "";
            String[] splits;
            if (name != null) {
                name = name.replace("\t", "").trim();
            }
            if (charaterStr != null) {
                charaterStr = charaterStr.replace("\t", " ");
                splits = charaterStr.trim().split(":");
                if (splits.length > 1)
                    charaters = splits[1];
            }
            if (directorStr != null) {
                directorStr = directorStr.replace("\t", " ");
                splits = directorStr.trim().split(":");
                if (splits.length > 1)
                    directors = splits[1];
            }
            if (performerStr != null) {
                performerStr = performerStr.replace("\t", " ");
                splits = performerStr.trim().split(":");
                if (splits.length > 1)
                    performers = splits[1];
            }

            film.setName(name);
            film.setCharater(charaters);
            film.setDirector(directors);
            film.setPerformer(performers);
            filmList.add(film);
        }
        filereader.close();
        return filmList;
    }

}

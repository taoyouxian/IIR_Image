package cn.edu.ruc.iir.api.agent.service;

import cn.edu.ruc.iir.api.agent.domain.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @version V1.0
 * @Package: cn.edu.ruc.iir.api.agent.service
 * @ClassName: StarServiceI
 * @Description:
 * @author: tao
 * @date: Create in 2018-06-18 16:12
 **/
@Component
public interface StarServiceI {

    List<Prize> getPrize(String star_name) throws Exception;

    List<Experience> getExperience(String star_name) throws Exception;

    Intro getIntro(String star_name) throws Exception;

    List<Relation> getRelation(String star_name) throws Exception;

    List<BasicInfo> getBasicinfo(String[] star_name_list) throws Exception;

    BasicInfo getBasicinfo(String star_name) throws Exception;

    BasicInfo getBasicinfo(String star_name, String new_path) throws Exception;

    List<HotStar> getHotStar() throws Exception;

    List<Work> getTV(String star_name) throws Exception;

    Movie getContent(String star_name, String new_path) throws IOException;

    List<Work> getFilm(String star_name) throws Exception;

    List<Work> getFilmByPath(String new_path) throws Exception;

}

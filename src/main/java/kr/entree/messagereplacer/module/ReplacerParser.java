package kr.entree.messagereplacer.module;

import java.util.Map;

/**
 * Created by JunHyeong Lim on 2019-03-11
 */
public interface ReplacerParser {
    Replacer parse(Map<?, ?> data);
}

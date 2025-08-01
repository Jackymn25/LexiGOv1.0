package entity;

import java.util.UUID;

public class CommonCardFactory implements CardFactory {

    @Override
    public CommonCard create(UUID wordId, String text, String translation, String example) {
        return new CommonCard(wordId, text, translation, example);
    }
}

package PrEis.test;

import PrEis.Testbed;
import PrEis.utils.FileWriteUtil;


public class TestFileWriteUtil {


  public static void runTests(Testbed p){
    String pfix = p.sketchPath()+"/"+Testbed.TEST_DIRS.getString(TestAssetKey.TEST_OUT_DIR.s());

    FileWriteUtil.writeOneCall(p, pfix+"oneWrite_lg.txt", x_lg);
    FileWriteUtil.writeOneCall(p, pfix+"oneWrite_sm_asArray.txt", x_sm);
    FileWriteUtil.writeOneCall(p, pfix+"oneWrite_sm_asParms.txt", x_sm[0], x_sm[1], x_sm[2], x_sm[3], x_sm[4], x_sm[5], x_sm[6], x_sm[7], x_sm[8]);

    /*--------------------------------------------------------------------------
    | NOTE: The following cases (accidentally) omit suffix of `.txt` to filename
    | parm. I'm keeping this omission to demo the contingency handling thereof.
    +-------------------------------------------------------------------------*/
    FileWriteUtil fw = new FileWriteUtil(p);

    fw.launchWrite(pfix+"objWrite_chain")
    .writeToFile(x_sm[3])
    .writeToFile(x_sm[4])
    .writeToFile(x_sm[5])
    .finishWrite();

    int busyTotaller = 0;
    fw.launchWrite(pfix+"objWrite_disjoint"); 
    for(int i=0; i<256; i++){busyTotaller++;}
    fw.writeToFile(x_sm[3]);
    for(int i=0; i<256; i++){busyTotaller++;}
    fw.writeToFile(x_sm[4]);
    for(int i=0; i<256; i++){busyTotaller++;}
    fw.writeToFile(x_sm[5]);
    for(int i=0; i<256; i++){busyTotaller++;}
    fw.writeToFile("does ["+busyTotaller+"]==[1024]");    
    fw.finishWrite();

    /*--------------------------------------------------------------------------
    | Note that the following omitted filenames, as to test such handling.
    +-------------------------------------------------------------------------*/
    fw.launchWriteTestAnnon(pfix)
    .writeToFile("purposely spec'd no filename!")
    .finishWrite();
  }

  /** <b>Small</b> Example <i>(Mythical Ores)</i> */
  public final static String[] x_sm = new String[]{
    "Mithril", "Galvornium", "Silima",
    "Aetherium", "Varlanite", "Welkynite",
    "Tiberium Riparius", "Tiberium Vinifera", "Tiberium Cruentus"
  };
  
  /** <b>Large</b> Example <i>(Beggar Prince)</i> */
  public final static String[] x_lg = new String[]{
    "[L#00] Beggar Prince",
    "[L#01] The story of Wheedle and his gifts from the Daedric Lord Namira",
    "[L#02] ",
    "[L#03] We look down upon the beggars of the Empire.",
    "[L#04] These lost souls are the poor and wretched of the land. ",
    "[L#05] Every city has its beggars.",
    "[L#06] Most are so poor they have only the clothes on their backs. ",
    "[L#07] They eat the scraps the rest of us throw out.",
    "[L#08] We toss them a coin so that we don't have to think too long about their plight.",
    "[L#09] ",
    "[L#10] Imagine my surprise when I heard the tale of the Beggar Prince.",
    "[L#11] I could not imagine what a Prince of Beggars would be. Here is the tale I heard...",
    "[L#12] ",
    "[L#13] It takes place in the first age, when gods walked like men and daedra haunted the wilderness with impunity.",
    "[L#14] It is a time before they were all confined to Oblivion.",
    "[L#15] There once was a man named Wheedle. Or maybe it was a woman.",
    "[L#16] The story goes to great lengths to avoid declaring Wheedle's gender.",
    "[L#17] Wheedle was the 13th child of a king in Valenwood.",
    "[L#18] As such Wheedle was in no position to take the throne or even inherit much property or wealth.",
    "[L#19] ",
    "[L#20] Wheedle had left the palace to find independent fortune and glory.",
    "[L#21] After many days of endless forest roads and tiny villages, Wheedle came upon a three men surrounding a beggar. ",
    "[L#22] The beggar was swaddled in rags from head to toe - no portion of the vagabond's body was visible. ",
    "[L#23] The men were intent on slaying the beggar.",
    "[L#24] With a cry of rage and indignation, Wheedle charged the men with sword drawn.",
    "[L#25] Being simple townsfolk, armed only with pitchforks and scythes, they immediately fled from the armored figure with the shining sword.",
    "[L#26] ",
    "[L#27] 'Many thanks for saving me,' wheezed the beggar from beneath the heap of foul rags.",
    "[L#28] Wheedle could barely stand the stench.",
    "[L#29] 'What is your name, wretch?' Wheedle asked. 'I am Namira', responded the beggar.",
    "[L#30] Unlike the townsfolk, Wheedle was well learned. That name meant nothing to them, but to Wheedle it was an opportunity.",
    "[L#31] 'You are the Daedric lord!' Wheedle exclaimed.",
    "[L#32] 'Why did you allow those men to harass you? For you could have slain them all with a whisper!'",
    "[L#33] ",
    "[L#34] 'I am pleased you recognized me', Namira rasped, 'I am frequently reviled by townsfolk.'",
    "[L#35] 'It pleases me to be recognized for my attribute, if not for my name.'",
    "[L#36] Wheedle knew that Namira was the Daedric lord of all thing gross and repulsive.",
    "[L#37] Diseases such as leprosy and gangrene were her domain.",
    "[L#38] Where others might have seen danger, Wheedle saw opportunity.",
    "[L#39] ",
    "[L#40] 'Oh, great Namira, let me apprentice myself to you.'",
    "[L#41] 'I ask only that you grant me powers to make my fortune and forge a name for myself that will live through the ages.'",
    "[L#42] 'Nay. I make my way alone in the world - I have no need for an apprentice.'",
    "[L#43] Namira shambled off down the road. Wheedle would not be put off.",
    "[L#44] ",
    "[L#45] With a bound, Wheedle was at Namira's heel, pressing the case for an apprenticeship.",
    "[L#46] For 33 days and night, Wheedle kept up the debate.",
    "[L#47] Namira said nothing, but Wheedle's voice was ceaseless.",
    "[L#48] Finally, on the 33rd day, Wheedle was too hoarse to talk.",
    "[L#49] ",
    "[L#50] Namira looked back on the suddenly silent figure.",
    "[L#51] Wheedle knelt in the mud at her feet, open hands raised in supplication.",
    "[L#52] 'It would seem you have completed your apprenticeship to me after all,' Namira declared.",
    "[L#53] 'I shall grant your request.'",
    "[L#54] Wheedle was overjoyed.",
    "[L#55] ",
    "[L#56] 'I grant you the power of disease.'",
    "[L#57] 'You may choose to be afflicted with any disease you choose, changing them at will, so long as it has visible symptoms.'",
    "[L#58] 'However, you must always bear at least one.'",
    "[L#59] 'I grant you the power of pity. You may evoke pity in anyone that sees you.'",
    "[L#60] 'Finally, I grant you the power of disregard. You may cause others to disregard your presence.'",
    "[L#61] Wheedle was aghast.",
    "[L#62] ",
    "[L#63] These were not boons from which a fortune could be made.",
    "[L#64] They were curses, each awful in its own right, but together they were unthinkable.",
    "[L#65] 'How am I to make my fortune and forge a name for myself with these terrible gifts?'",
    "[L#66] 'As you begged at my feet for 33 days and 33 nights, so shall you now beg for your fortune in the cities of men.'",
    "[L#67] 'Your name will become legendary among the beggars of Tamriel.'",
    "[L#68] 'The story of Wheedle, the Prince of Beggars, shall be handed down throughout the generations.'",
    "[L#69] ",
    "[L#70] It was as Namira predicted...",
    "[L#71] Wheedle was an irresistible beggar. None could see the wretch without desperately wanting to toss a coin at the huddled form.",
    "[L#72] However, Wheedle also discovered that the power of disregard gave great access to the secrets of the realms. ",
    "[L#73] People unknowingly said important things where Wheedle could hear them.",
    "[L#74] Wheedle grew to know the comings and goings of every citizen in the city.",
    "[L#75] ",
    "[L#76] To this day, it is said that if you really want to know something, go ask the beggars.",
    "[L#77] They have eyes and ears throughout the cities. ",
    "[L#78] They know all the little secrets of the daily lives of it's citizens.",
    "[L#79] ",
    "[L#80] ### THE END ###"
  };

}
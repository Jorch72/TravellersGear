package travellersgear.client.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import travellersgear.TravellersGear;
import travellersgear.client.gui.GuiButtonMoveableElement;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CustomizeableGuiHandler
{
	public static CustomizeableGuiHandler instance = new CustomizeableGuiHandler();
	
	public File configDir;
	public static List<GuiButtonMoveableElement> moveableInvElements = new ArrayList<GuiButtonMoveableElement>();
	public static int elementsNonSlotStart;
	public Configuration invConfig;
	public static ResourceLocation[] invTextures = {new ResourceLocation("travellersgear","textures/gui/inventory_book.png"),new ResourceLocation("travellersgear","textures/gui/inventory_digital.png"),new ResourceLocation("travellersgear","textures/gui/inventory_epic.png")};
	public static ResourceLocation invTexture = invTextures[0];

	public void preInit(FMLPreInitializationEvent event)
	{
		configDir = event.getModConfigurationDirectory();
		invConfig = new Configuration(new File(configDir,"TravellersGear_inv.cfg"));
	}
	public void init()
	{
		invConfig.load();
		moveableInvElements.clear();
		
		//ARMOR
		addElementWithConfig(moveableInvElements, invConfig, 0, 18,18, "Helmet", false, 25,30);
		addElementWithConfig(moveableInvElements, invConfig, 1, 18,18, "Chestplate", false, 25,48);
		addElementWithConfig(moveableInvElements, invConfig, 2, 18,18, "Leggings", false, 25,66);
		addElementWithConfig(moveableInvElements, invConfig, 3, 18,18, "Boots", false, 25,84);
		//TRAVELLERS GEAR
		addElementWithConfig(moveableInvElements, invConfig, 4, 18,18, "Cloak", false, 61,12);
		addElementWithConfig(moveableInvElements, invConfig, 5, 18,18, "Pauldrons", false, 97,30);
		addElementWithConfig(moveableInvElements, invConfig, 6, 18,18, "Vambraces", false, 97,66);
		addElementWithConfig(moveableInvElements, invConfig, 7, 18,18, "Title", false, 25,102);
		int slots = 8;
		if(TravellersGear.BAUBLES)
		{
			addElementWithConfig(moveableInvElements, invConfig, slots+0, 18,18, "Amulet", false, 43,12);
			addElementWithConfig(moveableInvElements, invConfig, slots+1, 18,18, "Ring 1", false, 43,102);
			addElementWithConfig(moveableInvElements, invConfig, slots+2, 18,18, "Ring 2", false, 61,102);
			addElementWithConfig(moveableInvElements, invConfig, slots+3, 18,18, "Belt", false, 97,48);
			slots+=4;
		}
		if(TravellersGear.MARI)
		{
			addElementWithConfig(moveableInvElements, invConfig, slots+0, 18,18, "Mariculture Ring", false, 79,102);
			addElementWithConfig(moveableInvElements, invConfig, slots+1, 18,18, "Mariculture Bracelet", false, 97,84);
			addElementWithConfig(moveableInvElements, invConfig, slots+2, 18,18, "Mariculture Necklace", false, 79,12);
			slots+=3;
		}
		if(TravellersGear.TCON)
		{
			addElementWithConfig(moveableInvElements, invConfig, slots+0, 18,18, "Tinkers Glove", false, 97,102);
			addElementWithConfig(moveableInvElements, invConfig, slots+1, 18,18, "Tinkers Knapsack", false, 97,12);
			addElementWithConfig(moveableInvElements, invConfig, slots+2, 18,18, "Tinkers Heart Red", false, 190,30);
			addElementWithConfig(moveableInvElements, invConfig, slots+3, 18,18, "Tinkers Heart Yellow", false, 190,48);
			addElementWithConfig(moveableInvElements, invConfig, slots+4, 18,18, "Tinkers Heart Green", false, 190,66);
			slots+=5;
		}

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addElementWithConfig(moveableInvElements, invConfig, slots+j+(i+1)*9, 18,18, "Inventory "+(j+(i*9)), false, 25+j*18+(j>4?6:0),120+i*18);
		slots+=27;
		for (int i = 0; i < 9; ++i)
			addElementWithConfig(moveableInvElements, invConfig, slots+i, 18,18, "Hotbar "+i, false, 25+i*18+(i>4?6:0),174);
		slots+=9;

		elementsNonSlotStart=slots;
		addElementWithConfig(moveableInvElements, invConfig, slots+0, 54,72, "Player", true, 43,30);
		addElementWithConfig(moveableInvElements, invConfig, slots+1, 80,10, "Name", true, 128,12);
		addElementWithConfig(moveableInvElements, invConfig, slots+2, 76, 8, "Tile", true, 130,22);
		addElementWithConfig(moveableInvElements, invConfig, slots+3, 70,20, "Experience", true, 133,29);
		addElementWithConfig(moveableInvElements, invConfig, slots+4, 64,10, "Health", true, 136,49);
		addElementWithConfig(moveableInvElements, invConfig, slots+5, 64,10, "Armor", true, 136,59);
		addElementWithConfig(moveableInvElements, invConfig, slots+6, 64,10, "Speed", true, 136,69);
		addElementWithConfig(moveableInvElements, invConfig, slots+7, 64,10, "Attack Strength", true, 136,79);
		addElementWithConfig(moveableInvElements, invConfig, slots+8, 18,162,"Potion Effects", true, 0, 22);
		if(TravellersGear.THAUM)
			addElementWithConfig(moveableInvElements, invConfig, slots+9, 64,30, "Vis Discounts", true, 136,89);
		invTexture = new ResourceLocation(invConfig.get("InvConfig", "TEXTURE", "travellersgear:textures/gui/inventory_book.png").getString());
		invConfig.save();
		createPresets();
	}

	GuiButtonMoveableElement addElementWithConfig(List<GuiButtonMoveableElement> addToList, Configuration invConfig, int id, int w, int h, String name, boolean b, int... def)
	{
		int[] xy = invConfig.get("InvConfig", name, def).getIntList();
		boolean hidden = invConfig.get("InvConfig", name+"_isHidden", false).getBoolean();
		GuiButtonMoveableElement bme = new GuiButtonMoveableElement(id, xy[0],xy[1], w,h, name,true);
		bme.hideElement = hidden;
		if(addToList!=null)
			addToList.add(bme);
		return bme;
	}
	GuiButtonMoveableElement addElement(List<GuiButtonMoveableElement> addToList, Configuration invConfig, int id, int w, int h, String name, boolean b, int... def)
	{
		GuiButtonMoveableElement bme = new GuiButtonMoveableElement(id, def[0],def[1], w,h, name,true);
		if(addToList!=null)
			addToList.add(bme);
		return bme;
	}

	public void writeElementsToConfig(Configuration invConfig)
	{
		invConfig.load();
		for(GuiButtonMoveableElement bme : moveableInvElements)
		{
			invConfig.get("InvConfig", bme.displayString, new int[]{bme.elementX,bme.elementY}).set(new int[]{bme.elementX,bme.elementY});
			invConfig.get("InvConfig", bme.displayString+"_isHidden", bme.hideElement).set(bme.hideElement);
		}
		invConfig.get("InvConfig", "TEXTURE", invTexture.getResourceDomain()+":"+invTexture.getResourcePath()).set(invTexture.getResourceDomain()+":"+invTexture.getResourcePath());
		invConfig.save();
	}

	public static HashMap<String,InvPreset> presets = new HashMap<String, InvPreset>();
	void createPresets()
	{
		List<GuiButtonMoveableElement> presetList = new ArrayList<GuiButtonMoveableElement>();
		//ARMOR
		addElement(presetList, invConfig, 0, 18,18, "Helmet", false, 25,30);
		addElement(presetList, invConfig, 1, 18,18, "Chestplate", false, 25,48);
		addElement(presetList, invConfig, 2, 18,18, "Leggings", false, 25,66);
		addElement(presetList, invConfig, 3, 18,18, "Boots", false, 25,84);
		//TRAVELLERS GEAR
		addElement(presetList, invConfig, 4, 18,18, "Cloak", false, 61,12);
		addElement(presetList, invConfig, 5, 18,18, "Pauldrons", false, 97,30);
		addElement(presetList, invConfig, 6, 18,18, "Vambraces", false, 97,66);
		addElement(presetList, invConfig, 7, 18,18, "Title", false, 25,102);
		int slots = 8;
		if(TravellersGear.BAUBLES)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Amulet", false, 43,12);
			addElement(presetList, invConfig, slots+1, 18,18, "Ring 1", false, 43,102);
			addElement(presetList, invConfig, slots+2, 18,18, "Ring 2", false, 61,102);
			addElement(presetList, invConfig, slots+3, 18,18, "Belt", false, 97,48);
			slots+=4;
		}
		if(TravellersGear.MARI)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Mariculture Ring", false, 79,102);
			addElement(presetList, invConfig, slots+1, 18,18, "Mariculture Bracelet", false, 97,84);
			addElement(presetList, invConfig, slots+2, 18,18, "Mariculture Necklace", false, 79,12);
			slots+=3;
		}
		if(TravellersGear.TCON)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Tinkers Glove", false, 97,102);
			addElement(presetList, invConfig, slots+1, 18,18, "Tinkers Knapsack", false, 97,12);
			addElement(presetList, invConfig, slots+2, 18,18, "Tinkers Heart Red", false, 190,30).hideElement=true;
			addElement(presetList, invConfig, slots+3, 18,18, "Tinkers Heart Yellow", false, 190,48).hideElement=true;
			addElement(presetList, invConfig, slots+4, 18,18, "Tinkers Heart Green", false, 190,66).hideElement=true;
			slots+=5;
		}

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addElement(presetList, invConfig, slots+j+(i+1)*9, 18,18, "Inventory "+(j+(i*9)), false, 25+j*18+(j>4?6:0),120+i*18);
		slots+=27;
		for (int i = 0; i < 9; ++i)
			addElement(presetList, invConfig, slots+i, 18,18, "Hotbar "+i, false, 25+i*18+(i>4?6:0),174);
		slots+=9;

		elementsNonSlotStart=slots;
		addElement(presetList, invConfig, slots+0, 54,72, "Player", true, 43,30);
		addElement(presetList, invConfig, slots+1, 80,10, "Name", true, 128,12);
		addElement(presetList, invConfig, slots+2, 76, 8, "Tile", true, 130,22);
		addElement(presetList, invConfig, slots+3, 70,20, "Experience", true, 133,29);
		addElement(presetList, invConfig, slots+4, 64,10, "Health", true, 136,49);
		addElement(presetList, invConfig, slots+5, 64,10, "Armor", true, 136,59);
		addElement(presetList, invConfig, slots+6, 64,10, "Speed", true, 136,69);
		addElement(presetList, invConfig, slots+7, 64,10, "Attack Strength", true, 136,79);
		addElement(presetList, invConfig, slots+8, 18,162,"Potion Effects", true, 0, 22);
		if(TravellersGear.THAUM)
			addElement(presetList, invConfig, slots+9, 64,30, "Vis Discounts", true, 136,89);
		presets.put("Book", new InvPreset(invTextures[0],presetList));


		presetList = new ArrayList<GuiButtonMoveableElement>();
		//ARMOR
		addElement(presetList, invConfig, 0, 18,18, "Helmet", false, 64,22);
		addElement(presetList, invConfig, 1, 18,18, "Chestplate", false, 64,42);
		addElement(presetList, invConfig, 2, 18,18, "Leggings", false, 64,62);
		addElement(presetList, invConfig, 3, 18,18, "Boots", false, 64,82);
		//TRAVELLERS GEAR
		addElement(presetList, invConfig, 4, 18,18, "Cloak", false, 136,42);
		addElement(presetList, invConfig, 5, 18,18, "Pauldrons", false, 136,22);
		addElement(presetList, invConfig, 6, 18,18, "Vambraces", false, 136,62);
		addElement(presetList, invConfig, 7, 18,18, "Title", false, 44,22);
		slots = 8;
		if(TravellersGear.BAUBLES)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Amulet", false, 156,22);
			addElement(presetList, invConfig, slots+1, 18,18, "Ring 1", false, 156,42);
			addElement(presetList, invConfig, slots+2, 18,18, "Ring 2", false, 156,62);
			addElement(presetList, invConfig, slots+3, 18,18, "Belt", false, 156,82);
			slots+=4;
		}
		if(TravellersGear.MARI)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Mariculture Ring", false, 44,82);
			addElement(presetList, invConfig, slots+1, 18,18, "Mariculture Bracelet", false, 44,62);
			addElement(presetList, invConfig, slots+2, 18,18, "Mariculture Necklace", false, 44,42);
			slots+=3;
		}
		if(TravellersGear.TCON)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Tinkers Glove", false, 136,82);
			addElement(presetList, invConfig, slots+1, 18,18, "Tinkers Knapsack", false, 176,22).hideElement=true;
			addElement(presetList, invConfig, slots+2, 18,18, "Tinkers Heart Red", false, 176,42).hideElement=true;
			addElement(presetList, invConfig, slots+3, 18,18, "Tinkers Heart Yellow", false, 176,62).hideElement=true;
			addElement(presetList, invConfig, slots+4, 18,18, "Tinkers Heart Green", false, 176,82).hideElement=true;
			slots+=5;
		}

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addElement(presetList, invConfig, slots+j+(i+1)*9, 18,18, "Inventory "+(j+(i*9)), false, 24+j*18+(j>7?8:j>6?6:j>1?4:j>0?2:0),132+i*18);
		slots+=27;
		for (int i = 0; i < 9; ++i)
			addElement(presetList, invConfig, slots+i, 18,18, "Hotbar "+i, false, 4,22+i*18+(i/3));
		slots+=9;

		elementsNonSlotStart=slots;
		addElement(presetList, invConfig, slots+0, 54,72, "Player", true, 82,28);
		addElement(presetList, invConfig, slots+1, 80,10, "Name", true, 69,10);
		addElement(presetList, invConfig, slots+2, 76, 8, "Tile", true, 71,20);
		addElement(presetList, invConfig, slots+3, 70,20, "Experience", true, 74,100);
		addElement(presetList, invConfig, slots+4, 64,10, "Health", true, 82,110).hideElement=true;
		addElement(presetList, invConfig, slots+5, 64,10, "Armor", true, 109,110).hideElement=true;
		addElement(presetList, invConfig, slots+6, 64,10, "Speed", true, 82,120).hideElement=true;
		addElement(presetList, invConfig, slots+7, 64,10, "Attack Strength", true, 109,120).hideElement=true;
		addElement(presetList, invConfig, slots+8, 18,162,"Potion Effects", true, 196, 23);
		if(TravellersGear.THAUM)
			addElement(presetList, invConfig, slots+9, 64,30, "Vis Discounts", true, 130,101).hideElement=true;
		presets.put("Digital", new InvPreset(invTextures[1],presetList));


		presetList = new ArrayList<GuiButtonMoveableElement>();
		//ARMOR
		addElement(presetList, invConfig, 0, 18,18, "Helmet", false, 8,49);
		addElement(presetList, invConfig, 1, 18,18, "Chestplate", false, 8,67);
		addElement(presetList, invConfig, 2, 18,18, "Leggings", false, 8,85);
		addElement(presetList, invConfig, 3, 18,18, "Boots", false, 8,103);
		//TRAVELLERS GEAR
		addElement(presetList, invConfig, 4, 18,18, "Cloak", false, 44,31);
		addElement(presetList, invConfig, 5, 18,18, "Pauldrons", false, 80,49);
		addElement(presetList, invConfig, 6, 18,18, "Vambraces", false, 80,85);
		addElement(presetList, invConfig, 7, 18,18, "Title", false, 8,121);
		slots = 8;
		if(TravellersGear.BAUBLES)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Amulet", false, 26,31);
			addElement(presetList, invConfig, slots+1, 18,18, "Ring 1", false, 26,121);
			addElement(presetList, invConfig, slots+2, 18,18, "Ring 2", false, 44,121);
			addElement(presetList, invConfig, slots+3, 18,18, "Belt", false, 80,67);
			slots+=4;
		}
		if(TravellersGear.MARI)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Mariculture Ring", false, 62,121);
			addElement(presetList, invConfig, slots+1, 18,18, "Mariculture Bracelet", false, 80,103);
			addElement(presetList, invConfig, slots+2, 18,18, "Mariculture Necklace", false, 62,31);
			slots+=3;
		}
		if(TravellersGear.TCON)
		{
			addElement(presetList, invConfig, slots+0, 18,18, "Tinkers Glove", false, 80,121);
			addElement(presetList, invConfig, slots+1, 18,18, "Tinkers Knapsack", false, 80,31);
			addElement(presetList, invConfig, slots+2, 18,18, "Tinkers Heart Red", false, 23,180).hideElement=true;
			addElement(presetList, invConfig, slots+3, 18,18, "Tinkers Heart Yellow", false, 44,180).hideElement=true;
			addElement(presetList, invConfig, slots+4, 18,18, "Tinkers Heart Green", false, 65,180).hideElement=true;
			slots+=5;
		}

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				addElement(presetList, invConfig, slots+j+(i+1)*9, 18,18, "Inventory "+(j+(i*9)), false, 192-i*18,13+j*18);
		slots+=27;
		for (int i = 0; i < 9; ++i)
			addElement(presetList, invConfig, slots+i, 18,18, "Hotbar "+i, false, 138,13+i*18);
		slots+=9;

		elementsNonSlotStart=slots;
		addElement(presetList, invConfig, slots+0, 54,72, "Player", true, 26,49);
		addElement(presetList, invConfig, slots+1, 80,10, "Name", true, 13,13);
		addElement(presetList, invConfig, slots+2, 76, 8, "Tile", true, 13,23);
		addElement(presetList, invConfig, slots+3, 70,20, "Experience", true, 18,139);
		addElement(presetList, invConfig, slots+4, 64,10, "Health", true, 21,159);
		addElement(presetList, invConfig, slots+5, 64,10, "Armor", true, 21,169);
		addElement(presetList, invConfig, slots+6, 64,10, "Speed", true, 21,179).hideElement=true;
		addElement(presetList, invConfig, slots+7, 64,10, "Attack Strength", true, 21,189).hideElement=true;
		addElement(presetList, invConfig, slots+8, 18,162,"Potion Effects", true, 109, 13);
		if(TravellersGear.THAUM)
			addElement(presetList, invConfig, slots+9, 64,30, "Vis Discounts", true, 86,175).hideElement=true;
		presets.put("Epic Quest", new InvPreset(invTextures[2],presetList));
	}

	public class InvPreset
	{
		public final ResourceLocation texture;
		public final List<GuiButtonMoveableElement> elements;
		public InvPreset(ResourceLocation tex, List<GuiButtonMoveableElement> e)
		{
			this.texture = tex;
			this.elements = e;
		}

	}
}

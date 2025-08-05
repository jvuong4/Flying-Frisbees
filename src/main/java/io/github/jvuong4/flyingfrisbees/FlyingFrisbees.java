package io.github.jvuong4.flyingfrisbees;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyingFrisbees implements ModInitializer {
	public static final String ID = "mod_id";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Flying Frisbees flying freely for fun. Fetch!");
	}
}

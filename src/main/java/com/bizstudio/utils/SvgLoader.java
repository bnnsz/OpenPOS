/*
 * Copyright 2016 Andreas Fester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bizstudio.utils;

import com.bizstudio.utils.BufferedImageTranscoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;

public class SvgLoader {


    private static SvgLoader loader;


    private static final Logger logger = LogManager.getLogger();
    public static SvgLoader getInstance() {
        if (loader == null) {
            loader = new SvgLoader();
        }
        return loader;
    }
    private SvgLoader() {
    }

    public Image loadSvgImage(String url) {
        BufferedImageTranscoder trans = new BufferedImageTranscoder(100,100);

        try (InputStream file = getClass().getResourceAsStream(url)) {
            TranscoderInput transIn = new TranscoderInput(file);
            try {
                trans.transcode(transIn, null);
                return SwingFXUtils.toFXImage(trans.getBufferedImage(), null);
            } catch (TranscoderException ex) {
                logger.error("{}", ex);
            }
        } catch (IOException io) {
            logger.error("{}", io);
        }
        return null;
    }

}

/*
 * Applitools SDK for Selenium integration.
 */
package com.applitools.eyes.images;

import com.applitools.eyes.*;
import com.applitools.utils.ArgumentGuard;
import com.applitools.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.net.URI;

public class Eyes extends EyesBase {

    private String title;
    private EyesImagesScreenshot screenshot;
    private String inferred;

    /**
     * Creates a new (possibly disabled) Eyes instance that interacts
     * with the Eyes Server at the specified url.
     *
     * @param serverUrl  The Eyes server URL.
     */
    public Eyes(URI serverUrl) {
        super(serverUrl);
    }

    /**
     * Creates a new Eyes instance that interacts with the Eyes Server at the
     * specified url.
     */
    public Eyes() {
        this(getDefaultServerUrl());
    }

    @Override
    public String getBaseAgentId() {
        return "eyes.images.java/2.49";
    }

    /**
     * Starts a test.
     *
     * @param appName        The name of the application under test.
     * @param testName       The test name.
     * @param dimensions      Determines the resolution used for the baseline.
     *                       {@code null} will automatically grab the
     *                       resolution from the image.
     */
    @SuppressWarnings("UnusedDeclaration")
    public void open(String appName, String testName,
            RectangleSize dimensions) {
        openBase(appName, testName, dimensions, null);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * ﻿Starts a new test without setting the viewport size of the AUT.
     *
     * @see #open(String, String, RectangleSize)
     */
    public void open(String appName, String testName) {
        open(appName, testName, null);
    }


    @Deprecated
    @SuppressWarnings("UnusedDeclaration")
    /**
     * Superseded by {@link #checkImage(java.awt.image.BufferedImage)}.
     */
    public boolean checkWindow(BufferedImage image) {
        return checkImage(image);
    }

    @Deprecated
    @SuppressWarnings("UnusedDeclaration")
    /**
     * Superseded by {@link #checkImage(java.awt.image.BufferedImage, String)}.
     */
    public boolean checkWindow(BufferedImage image, String tag) {
        return checkImage(image, tag);
    }

    @Deprecated
    @SuppressWarnings("UnusedDeclaration")
    /**
     * Superseded by {@link #checkImage(java.awt.image.BufferedImage, String,
     * boolean)}.
     */
    public boolean checkWindow(BufferedImage image, String tag,
                            boolean ignoreMismatch) {
        return checkImage(image, tag, ignoreMismatch);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * See {@link #checkImage(BufferedImage, String)}.
     * {@code tag} defaults to {@code null}.
     */
    public boolean checkImage(BufferedImage image) {
        return checkImage(image, null);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * See {@link #checkImage(BufferedImage, String, boolean)}.
     * {@code ignoreMismatch} defaults to {@code false}.
     */
    public boolean checkImage(BufferedImage image, String tag) {
        return checkImage(image, tag, false);
    }

    /**
     * Matches the input image with the next expected image.
     *
     * @param image The image to perform visual validation for.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @param ignoreMismatch True if the server should ignore a negative
     *                       result for the visual validation.
     * @return True if the image matched the expected output, false otherwise.
     * @throws TestFailedException Thrown if a mismatch is detected and
     *                              immediate failure reports are enabled.
     */
    public boolean checkImage(BufferedImage image, String tag,
                              boolean ignoreMismatch) {
        if (getIsDisabled()) {
            logger.verbose(String.format(
                    "CheckImage(Image, '%s', %b): Ignored", tag,
                    ignoreMismatch));
            return false;
        }
        ArgumentGuard.notNull(image, "image cannot be null!");

        logger.verbose(String.format("CheckImage(Image, '%s', %b)",
                tag, ignoreMismatch));

        if (viewportSize == null) {
            setViewportSize(
                    new RectangleSize(image.getWidth(), image.getHeight())
            );
        }

        return checkImage_(new RegionProvider() {
            public Region getRegion() {
                return Region.EMPTY;
            }

            public CoordinatesType getCoordinatesType() {
                return null;
            }
        }, image, tag, ignoreMismatch);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * See {@link #checkImage(String, String)}.
     * {@code tag} defaults to {@code null}.
     */
    public boolean checkImage(String path) {
        return checkImage(path, null);
    }

    /**
     * See {@link #checkImage(String, String, boolean)}.
     * {@code ignoreMismatch} defaults to {@code false}.
     *
     * @param path The path to the image to check.
     * @param tag The tag to be associated with the visual checkpoint.
     * @return Whether or not the image matched the baseline.
     */
    public boolean checkImage(String path, String tag) {
        return checkImage(path, tag, false);
    }

    /**
     * Matches the image stored in the input file with the next expected image.
     * See {@link #checkImage(BufferedImage, String, boolean)}.
     *
     * @param path The base64 representation of the image's raw bytes.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @param ignoreMismatch True if the server should ignore a negative
     *                       result for the visual validation.
     * @return Whether or not the image matched the baseline.
     */
    public boolean checkImage(String path, String tag,
                              boolean ignoreMismatch) {
        return checkImage(ImageUtils.imageFromFile(path), tag, ignoreMismatch);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * See {@link #checkImage(byte[], String)}.
     * {@code tag} defaults to {@code null}.
     *
     * @param image The raw png bytes of the image to perform visual validation
     *              for.
     * @return Whether or not the image matched the baseline.
     */
    public boolean checkImage(byte[] image) {
        return checkImage(image, null);
    }

    /**
     * See {@link #checkImage(byte[], String, boolean)}.
     * {@code ignoreMismatch} defaults to {@code false}.
     *
     * @param image The raw png bytes of the image to perform visual validation
     *              for.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @return Whether or not the image matched the baseline.
     */
    public boolean checkImage(byte[] image, String tag) {
        return checkImage(image, tag, false);
    }

    /**
     * Matches the input image with the next expected image.
     * See {@link #checkImage(BufferedImage, String, boolean)}.
     *
     * @param image The raw png bytes of the image to perform visual validation
     *              for.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @param ignoreMismatch True if the server should ignore a negative
     *                       result for the visual validation.
     * @return Whether or not the image matched the baseline.
     */
    public boolean checkImage(byte[] image, String tag,
                              boolean ignoreMismatch) {
        return checkImage(ImageUtils.imageFromBytes(image), tag,
                ignoreMismatch);
    }

    /**
     * Perform visual validation for the current image.
     *
     * @param image The image to perform visual validation for.
     * @param region The region to validate within the image.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @param ignoreMismatch True if the server should ignore a negative
     *                       result for the visual validation.
     * @throws TestFailedException Thrown if a mismatch is detected and
     *                              immediate failure reports are enabled.
     * @return Whether or not the image matched the baseline.
     */
    public boolean checkRegion(BufferedImage image, final Region region,
                               String tag, boolean ignoreMismatch) {
        if (getIsDisabled()) {
            logger.verbose(String.format(
                    "CheckRegion(Image, [%s], '%s', %b): Ignored",
                    region, tag, ignoreMismatch));
            return false;
        }
        ArgumentGuard.notNull(image, "image cannot be null!");
        ArgumentGuard.notNull(region, "region cannot be null!");

        logger.verbose(String.format("CheckRegion(Image, [%s], '%s', %b)",
                region, tag, ignoreMismatch));

        if (viewportSize == null) {
            setViewportSize(
                    new RectangleSize(image.getWidth(), image.getHeight())
            );
        }

        return checkImage_(new RegionProvider() {
            public Region getRegion() {
                return region;
            }

            public CoordinatesType getCoordinatesType() {
                return CoordinatesType.SCREENSHOT_AS_IS;
            }
        }, image, tag, ignoreMismatch);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * Perform visual validation for a region in a given image. Does not
     * ignore mismatches.
     *
     * @param image The image to perform visual validation for.
     * @param region The region to validate within the image.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @throws TestFailedException Thrown if a mismatch is detected and
     *                              immediate failure reports are enabled.
     */
    public void checkRegion(BufferedImage image, Region region, String tag){
        checkRegion(image, region, tag, false);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * Perform visual validation of a region for a given image. Tag is empty
     * and mismatches are not ignored.
     *
     * @param image The image to perform visual validation for.
     * @param region The region to validate within the image.
     * @throws TestFailedException Thrown if a mismatch is detected and
     *                              immediate failure reports are enabled.
     */
    public void checkRegion(BufferedImage image, Region region){
        checkRegion(image, region, null, false);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * Adds a mouse trigger.
     *
     * @param action  Mouse action.
     * @param control The control on which the trigger is activated (context
     *                relative coordinates).
     * @param cursor  The cursor's position relative to the control.
     */
    public void addMouseTrigger(MouseAction action, Region control,
            Location cursor) {
        addMouseTriggerBase(action, control, cursor);
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * Adds a keyboard trigger.
     *
     * @param control The control's context-relatieve region.
     * @param text    The trigger's text.
     */
    public void addTextTrigger(Region control, String text) {
        addTextTriggerBase(control, text);
    }

    @Override
    public RectangleSize getViewportSize() {
        return viewportSize;
    }

    @Override
    public void setViewportSize(RectangleSize size) {
        ArgumentGuard.notNull(size, "size");
        this.viewportSize = new RectangleSize(size.getWidth(),
                size.getHeight());
    }

    @Override
    protected String getInferredEnvironment() {
        return inferred != null ? inferred : "";
    }

    @SuppressWarnings("UnusedDeclaration")
    /**
     * Sets the inferred environment for the test.
     * @param inferred The inferred environment string.
     */
    public void setInferredEnvironment(String inferred) {
        this.inferred = inferred;
    }

    @Override
    public EyesScreenshot getScreenshot() {
        return screenshot;
    }

    @Override
    protected String getTitle() {

        return title;
    }


    /**
     * See {@link #checkImage_(RegionProvider, String, boolean)}.
     *
     * @param regionProvider The region for which verification will be
     *                       performed. see
     *                       {@link #checkWindowBase(RegionProvider, String,
     *                       boolean, int)}.
     * @param image The image to perform visual validation for.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @param ignoreMismatch True if the server should ignore a negative
     *                       result for the visual validation.
     * @return True if the image matched the expected output, false otherwise.
     */
    private boolean checkImage_(RegionProvider regionProvider,
                                BufferedImage image,
                                String tag,
                                boolean ignoreMismatch) {

        // Set the screenshot to be verified.
        screenshot = new EyesImagesScreenshot(image);

        return checkImage_(regionProvider, tag, ignoreMismatch);
    }


    /**
     * Internal function for performing an image verification for a region of
     * a buffered image.
     *
     * @param regionProvider The region for which verification will be
     *                       performed. see
     *                       {@link #checkWindowBase(RegionProvider, String,
     *                       boolean, int)}.
     * @param tag An optional tag to be associated with the validation
     *            checkpoint.
     * @param ignoreMismatch True if the server should ignore a negative
     *                       result for the visual validation.
     * @return True if the image matched the expected output, false otherwise.
     */
    private boolean checkImage_(RegionProvider regionProvider, String tag,
                                boolean ignoreMismatch) {

        // Set the title to be linked to the screenshot.
        title = (tag != null) ? tag : "";

        MatchResult mr = checkWindowBase(regionProvider, tag,
                ignoreMismatch);

        return mr.getAsExpected();
    }
}
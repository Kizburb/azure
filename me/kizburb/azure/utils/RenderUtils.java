package azure.me.kizburb.azure.utils;

import java.awt.Color;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;

public class RenderUtils {

	public static final RenderItem RENDER_ITEM = new RenderItem(Minecraft.getMinecraft().renderEngine,
			Minecraft.getMinecraft().modelManager);
	private static ScaledResolution scaledResolution;

	public static void drawOutlinedBoundingBox(AxisAlignedBB bb, int color) {
		float[] color1;
		float red = (color >> 16 & 0xFF) / 255.0F;
		float blue = (color >> 8 & 0xFF) / 255.0F;
		float green = (color & 0xFF) / 255.0F;
		float alpha = (color >> 24 & 0xFF) / 255.0F;
		color1 = new float[] { red, blue, green, alpha };

		GL11.glLineWidth(1.0F);
		GL11.glColor4f(color1[0], color1[1], color1[2], 0.8F);
		RenderGlobal.func_181561_a(bb);
		GlStateManager.disableDepth();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public static void box(double x, double y, double z, double x2, double y2, double z2, float red, float green,
			float blue, float alpha) {
		x = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 = x2 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 = y2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 = z2 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawColorBox(new AxisAlignedBB(x, y, z, x2, y2, z2), red, green, blue, alpha);
		GL11.glColor4d(0, 0, 0, 0.5F);
		drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts X.
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		tessellator.draw();// Ends X.
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Y.
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		tessellator.draw();// Ends Y.
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Z.
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		tessellator.draw();
		worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha)
				.endVertex();
		tessellator.draw();// Ends Z.
	}

	public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.begin(3, DefaultVertexFormats.POSITION);
		worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(3, DefaultVertexFormats.POSITION);
		worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		tessellator.draw();
		worldRenderer.begin(1, DefaultVertexFormats.POSITION);
		worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		tessellator.draw();
	}

	public static void enableLighting() {
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 2848);
		GL11.glDisable((int) 3042);
		OpenGlHelper.setActiveTexture((int) OpenGlHelper.lightmapTexUnit);
		GL11.glMatrixMode((int) 5890);
		GL11.glLoadIdentity();
		float var3 = 0.0039063F;
		GL11.glScalef((float) var3, (float) var3, (float) var3);
		GL11.glTranslatef((float) 8.0f, (float) 8.0f, (float) 8.0f);
		GL11.glMatrixMode((int) 5888);
		GL11.glTexParameteri((int) 3553, (int) 10241, (int) 9729);
		GL11.glTexParameteri((int) 3553, (int) 10240, (int) 9729);
		GL11.glTexParameteri((int) 3553, (int) 10242, (int) 10496);
		GL11.glTexParameteri((int) 3553, (int) 10243, (int) 10496);
		GL11.glColor4f((float) 1.0F, (float) 1.0F, (float) 1.0F, (float) 1.0F);
		OpenGlHelper.setActiveTexture((int) OpenGlHelper.defaultTexUnit);
	}

	public static void disableLighting() {
		OpenGlHelper.setActiveTexture((int) OpenGlHelper.lightmapTexUnit);
		GL11.glDisable((int) 3553);
		OpenGlHelper.setActiveTexture((int) OpenGlHelper.defaultTexUnit);
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glDisable((int) 2896);
		GL11.glDisable((int) 3553);
	}

	public static void enableGL2D() {
		GL11.glDisable((int) 2929);
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDepthMask((boolean) true);
		GL11.glEnable((int) 2848);
		GL11.glHint((int) 3154, (int) 4354);
		GL11.glHint((int) 3155, (int) 4354);
	}

	public static void disableGL2D() {
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 2929);
		GL11.glDisable((int) 2848);
		GL11.glHint((int) 3154, (int) 4352);
		GL11.glHint((int) 3155, (int) 4352);
	}

	public static void enableGL3D(float lineWidth) {
		GL11.glDisable((int) 3008);
		GL11.glEnable((int) 3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDisable((int) 3553);
		GL11.glDisable((int) 2929);
		GL11.glDepthMask((boolean) false);
		GL11.glEnable((int) 2884);
		Minecraft.getMinecraft().entityRenderer.disableLightmap();
		GL11.glEnable((int) 2848);
		GL11.glHint((int) 3154, (int) 4354);
		GL11.glHint((int) 3155, (int) 4354);
		GL11.glLineWidth((float) lineWidth);
	}

	public static void disableGL3D() {
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		GL11.glDisable((int) 3042);
		GL11.glEnable((int) 3008);
		GL11.glDepthMask((boolean) true);
		GL11.glCullFace((int) 1029);
		GL11.glDisable((int) 2848);
		GL11.glHint((int) 3154, (int) 4352);
		GL11.glHint((int) 3155, (int) 4352);
	}

	public static void drawRect(float x, float y, float x1, float y1, int color) {
		RenderUtils.enableGL2D();
		RenderUtils.glColor(color);
	}

	public static void drawRect(float x, float y, float x1, float y1) {
		GL11.glBegin((int) 7);
		GL11.glVertex2f((float) x, (float) y1);
		GL11.glVertex2f((float) x1, (float) y1);
		GL11.glVertex2f((float) x1, (float) y);
		GL11.glVertex2f((float) x, (float) y);
		GL11.glEnd();
	}

	public static void drawRect(float x, float y, float x1, float y1, float r, float g, float b, float a) {
		RenderUtils.enableGL2D();
		GL11.glColor4f((float) r, (float) g, (float) b, (float) a);
		RenderUtils.drawRect(x, y, x1, y1);
		RenderUtils.disableGL2D();
	}

	public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glShadeModel((int) 7425);
		GL11.glPushMatrix();
		GL11.glBegin((int) 7);
		RenderUtils.glColor(col1);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		RenderUtils.glColor(col2);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		GL11.glDisable((int) 2848);
		GL11.glShadeModel((int) 7424);
	}

	public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
		RenderUtils.enableGL2D();
		GL11.glShadeModel((int) 7425);
		GL11.glBegin((int) 7);
		RenderUtils.glColor(topColor);
		GL11.glVertex2f((float) x, (float) y1);
		GL11.glVertex2f((float) x1, (float) y1);
		RenderUtils.glColor(bottomColor);
		GL11.glVertex2f((float) x1, (float) y);
		GL11.glVertex2f((float) x, (float) y);
		GL11.glEnd();
		GL11.glShadeModel((int) 7424);
		RenderUtils.disableGL2D();
	}

	public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
		RenderUtils.enableGL2D();
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		RenderUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
		RenderUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
		RenderUtils.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
		RenderUtils.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
		RenderUtils.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
		RenderUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
		RenderUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
		RenderUtils.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
		RenderUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		RenderUtils.disableGL2D();
	}

	public static void drawBorderedRect(Rectangle rectangle, float width, int internalColor, int borderColor) {
		float x = rectangle.x;
		float y = rectangle.y;
		float x1 = rectangle.x + rectangle.width;
		float y1 = rectangle.y + rectangle.height;
		RenderUtils.enableGL2D();
		RenderUtils.glColor(internalColor);
		RenderUtils.drawRect(x + width, y + width, x1 - width, y1 - width);
		RenderUtils.glColor(borderColor);
		RenderUtils.drawRect(x + 1.0f, y, x1 - 1.0f, y + width);
		RenderUtils.drawRect(x, y, x + width, y1);
		RenderUtils.drawRect(x1 - width, y, x1, y1);
		RenderUtils.drawRect(x + 1.0f, y1 - width, x1 - 1.0f, y1);
		RenderUtils.disableGL2D();
	}

	public static void drawBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int inside,
			int border) {
		RenderUtils.enableGL2D();
		RenderUtils.drawRect(x, y, x1, y1, inside);
		RenderUtils.glColor(border);
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glLineWidth((float) lineWidth);
		GL11.glBegin((int) 3);
		GL11.glVertex2f((float) x, (float) y);
		GL11.glVertex2f((float) x, (float) y1);
		GL11.glVertex2f((float) x1, (float) y1);
		GL11.glVertex2f((float) x1, (float) y);
		GL11.glVertex2f((float) x, (float) y);
		GL11.glEnd();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		RenderUtils.disableGL2D();
	}

	public static void drawGradientBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth,
			int border, int bottom, int top) {
		RenderUtils.enableGL2D();
		RenderUtils.drawGradientRect(x, y, x1, y1, top, bottom);
		RenderUtils.glColor(border);
		GL11.glEnable((int) 3042);
		GL11.glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glLineWidth((float) lineWidth);
		GL11.glBegin((int) 3);
		GL11.glVertex2f((float) x, (float) y);
		GL11.glVertex2f((float) x, (float) y1);
		GL11.glVertex2f((float) x1, (float) y1);
		GL11.glVertex2f((float) x1, (float) y);
		GL11.glVertex2f((float) x, (float) y);
		GL11.glEnd();
		GL11.glEnable((int) 3553);
		GL11.glDisable((int) 3042);
		RenderUtils.disableGL2D();
	}

	public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor,
			int borderColor) {
		RenderUtils.enableGL2D();
		RenderUtils.glColor(internalColor);
		RenderUtils.drawRect(x + width, y + width, x1 - width, y1 - width);
		RenderUtils.glColor(borderColor);
		RenderUtils.drawRect(x + width, y, x1 - width, y + width);
		RenderUtils.drawRect(x, y, x + width, y1);
		RenderUtils.drawRect(x1 - width, y, x1, y1);
		RenderUtils.drawRect(x + width, y1 - width, x1 - width, y1);
		RenderUtils.disableGL2D();
	}

	public static void drawBorderedRect(int x, int y, int x1, int y1, int insideC, int borderC) {
		RenderUtils.enableGL2D();
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		RenderUtils.drawVLine(x *= 2, y *= 2, (y1 *= 2) - 1, borderC);
		RenderUtils.drawVLine((x1 *= 2) - 1, y, y1, borderC);
		RenderUtils.drawHLine(x, x1 - 1, y, borderC);
		RenderUtils.drawHLine(x, x1 - 2, y1 - 1, borderC);
		RenderUtils.drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		RenderUtils.disableGL2D();
	}

	public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
		float yc;
		float xc;
		float a;
		int i;
		float f1 = (float) (color >> 24 & 255) / 255.0f;
		float f2 = (float) (color >> 16 & 255) / 255.0f;
		float f3 = (float) (color >> 8 & 255) / 255.0f;
		float f4 = (float) (color & 255) / 255.0f;
		GL11.glPushMatrix();
		GL11.glTranslated((double) x, (double) y, (double) 0.0);
		GL11.glColor4f((float) f2, (float) f3, (float) f4, (float) f1);
		GL11.glLineWidth((float) width);
		if (angle > 0.0) {
			GL11.glBegin((int) 3);
			i = 0;
			while ((double) i < angle) {
				a = (float) ((double) i * (angle * 3.141592653589793 / (double) points));
				xc = (float) (Math.cos(a) * (double) radius);
				yc = (float) (Math.sin(a) * (double) radius);
				GL11.glVertex2f((float) xc, (float) yc);
				++i;
			}
			GL11.glEnd();
		}
		if (angle < 0.0) {
			GL11.glBegin((int) 3);
			i = 0;
			while ((double) i > angle) {
				a = (float) ((double) i * (angle * 3.141592653589793 / (double) points));
				xc = (float) (Math.cos(a) * (double) (-radius));
				yc = (float) (Math.sin(a) * (double) (-radius));
				GL11.glVertex2f((float) xc, (float) yc);
				--i;
			}
			GL11.glEnd();
		}
		RenderUtils.disableGL2D();
		GL11.glDisable((int) 3479);
		GL11.glPopMatrix();
	}

	public static void glColor(Color color) {
		GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f),
				(float) ((float) color.getBlue() / 255.0f), (float) ((float) color.getAlpha() / 255.0f));
	}

	public static void glColor(int hex) {
		float alpha = (float) (hex >> 24 & 255) / 255.0f;
		float red = (float) (hex >> 16 & 255) / 255.0f;
		float green = (float) (hex >> 8 & 255) / 255.0f;
		float blue = (float) (hex & 255) / 255.0f;
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
	}

	public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
		float red = 0.003921569f * (float) redRGB;
		float green = 0.003921569f * (float) greenRGB;
		float blue = 0.003921569f * (float) blueRGB;
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
	}

	public static void updateScaledResolution() {
		scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
	}

	public static ScaledResolution getScaledResolution() {
		return scaledResolution;
	}

	public static void prepareScissorBox(float x, float y, float x2, float y2) {
		RenderUtils.updateScaledResolution();
		int factor = scaledResolution.getScaleFactor();
		GL11.glScissor((int) ((int) (x * (float) factor)),
				(int) ((int) (((float) scaledResolution.getScaledHeight() - y2) * (float) factor)),
				(int) ((int) ((x2 - x) * (float) factor)), (int) ((int) ((y2 - y) * (float) factor)));
	}

	public static void drawOutlinedBox(AxisAlignedBB boundingBox) {
		if (boundingBox == null) {
			return;
		}
		GL11.glBegin((int) 3);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 3);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glEnd();
		GL11.glBegin((int) 1);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.maxZ, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glVertex3d((double) boundingBox.minX, (double) boundingBox.maxZ, (double) boundingBox.maxZ);
		GL11.glEnd();
	}

	private static double getDiff(double lastI, double i, float ticks, double ownI) {
		return lastI + (i - lastI) * (double) ticks - ownI;
	}

	public static void drawFullCircle(int cx, int cy, double r, int c) {
		r *= 2.0;
		cx *= 2;
		cy *= 2;
		float f = (float) (c >> 24 & 255) / 255.0f;
		float f1 = (float) (c >> 16 & 255) / 255.0f;
		float f2 = (float) (c >> 8 & 255) / 255.0f;
		float f3 = (float) (c & 255) / 255.0f;
		RenderUtils.enableGL2D();
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 6);
		int i = 0;
		while (i <= 360) {
			double x = Math.sin((double) i * 3.141592653589793 / 180.0) * r;
			double y = Math.cos((double) i * 3.141592653589793 / 180.0) * r;
			GL11.glVertex2d((double) ((double) cx + x), (double) ((double) cy + y));
			++i;
		}
		GL11.glEnd();
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		RenderUtils.disableGL2D();
	}

	public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
		GL11.glPushMatrix();
		cx *= 2.0f;
		cy *= 2.0f;
		float f = (float) (c >> 24 & 255) / 255.0f;
		float f1 = (float) (c >> 16 & 255) / 255.0f;
		float f2 = (float) (c >> 8 & 255) / 255.0f;
		float f3 = (float) (c & 255) / 255.0f;
		float theta = (float) (6.2831852 / (double) num_segments);
		float p = (float) Math.cos(theta);
		float s = (float) Math.sin(theta);
		float x = r *= 2.0f;
		float y = 0.0f;
		RenderUtils.enableGL2D();
		GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 2);
		int ii = 0;
		while (ii < num_segments) {
			GL11.glVertex2f((float) (x + cx), (float) (y + cy));
			float t = x;
			x = p * x - s * y;
			y = s * t + p * y;
			++ii;
		}
		GL11.glEnd();
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		RenderUtils.disableGL2D();
		GL11.glPopMatrix();
	}

	public static void drawHLine(float x, float y, float x1, int y1) {
		if (y < x) {
			float var5 = x;
			x = y;
			y = var5;
		}
		RenderUtils.drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
	}

	public static void drawVLine(float x, float y, float x1, int y1) {
		if (x1 < y) {
			float var5 = y;
			y = x1;
			x1 = var5;
		}
		RenderUtils.drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
	}

	public static void drawHLine(float x, float y, float x1, int y1, int y2) {
		if (y < x) {
			float var5 = x;
			x = y;
			y = var5;
		}
		RenderUtils.drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
	}

}

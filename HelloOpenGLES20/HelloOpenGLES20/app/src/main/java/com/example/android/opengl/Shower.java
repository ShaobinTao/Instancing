/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Shower {

    public Shower() {
        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram2 = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram2, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram2, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram2);                  // create OpenGL program executables

        MyGLRenderer.checkGlError("t1");

        GLES20.glUseProgram(mProgram2);

        MyGLRenderer.checkGlError("t1");


        mSampler = GLES20.glGetUniformLocation(mProgram2, "uTexture");
        MyGLRenderer.checkGlError("t1");
        muAlpha = GLES20.glGetUniformLocation(mProgram2, "uAlpha");
        MyGLRenderer.checkGlError("t1");
        muInfluence = GLES20.glGetUniformLocation(mProgram2, "uInfluence");
        MyGLRenderer.checkGlError("t1");
        muMVPMatrix = GLES20.glGetUniformLocation(mProgram2, "uMVPMatrix");
        MyGLRenderer.checkGlError("t1");

        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram2, "aTextureCoord");
        mVertCoordHandle = GLES20.glGetAttribLocation(mProgram2, "aVertexCoord");

        MyGLRenderer.checkGlError("t1");

        GLES20.glGenBuffers(1, mTexCoordBuf, 0);
        GLES20.glGenBuffers(1, mVertCoordBuf, 0);
        GLES20.glGenBuffers(1, mIndexBuf, 0);

        MyGLRenderer.checkGlError("t1");

        // texture coordinates buffer
        {
            ByteBuffer buf = ByteBuffer.allocateDirect(texCoords.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            textureBuffer = buf.asFloatBuffer();
            textureBuffer.put(texCoords);
            textureBuffer.position(0);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, textureBuffer.capacity() * 4, textureBuffer, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");

        {
            ByteBuffer buf = ByteBuffer.allocateDirect(vertCoords.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            vertBuffer = buf.asFloatBuffer();
            vertBuffer.put(vertCoords);
            vertBuffer.position(0);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertBuffer.capacity() * 4, vertBuffer, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");
        {
            ByteBuffer buf = ByteBuffer.allocateDirect(vertIndex.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            indexBuffer = buf.asIntBuffer();
            indexBuffer.put(vertIndex);
            indexBuffer.position(0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * 4, indexBuffer, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");
    }

    public void draw() {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)                                                                                                                                                                                                              ; // 225392
        GLES20.glDepthFunc(GLES20.GL_LESS)                                                                                                                                                                                                                ; // 225393
        GLES20.glDepthMask(true)                                                                                                                                                                                                                ; // 225394
        GLES20.glClearDepthf(1.0f)                                                                                                                                                                                                                     ; // 225395
        GLES20.glEnable(GLES20.GL_CULL_FACE)                                                                                                                                                                                                               ; // 225397
        GLES20.glCullFace(GLES20.GL_BACK)                                                                                                                                                                                                                 ; // 225398
        GLES20.glFrontFace(GLES20.GL_CCW)                                                                                                                                                                                                                 ; // 225399
        GLES20.glEnable(GLES20.GL_BLEND)                                                                                                                                                                                                                   ; // 225400
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE)                                                                                                                                                                                        ; // 225401
        GLES20.glDisable(GLES20.GL_DEPTH_TEST)                                                                                                                                                                                                             ; // 225402
        GLES20.glDepthMask(false)                                                                                                                                                                                                               ; // 225403





        

GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195461	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195462	
GLES20.glUseProgram(mProgram2)        ;//195463	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195464	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195465	
GLES20.glUniform1i(mSampler,0)  ;//195466	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195467	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195468	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195469	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195470	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195471	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195472	
GLES20.glUniform1f(muAlpha, 0.20416708f)    ;//195473
GLES20.glUniform1f(muInfluence,0.0f);//195474	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195475	
{float value[]={-3.448004f, 0.0f, 7.141306E-8f, 7.023268E-8f, 0.0f, 1.9395024f, 0.0f, 0.0f, 3.0143408E-7f, 0.0f, 0.8168702f, 0.8033682f, 1.5441458f, -0.4605259f, -0.5388384f, 1.4535393f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//195476	
}

GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195477	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195478	

GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195479	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195480	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195481	
GLES20.glUseProgram(mProgram2)        ;//195482	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195483	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195484	
GLES20.glUniform1i(mSampler,0)  ;//195485	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195486	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195487	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195488	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195489	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195490	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195491	
GLES20.glUniform1f(muAlpha, 0.27980208f)    ;//195492
GLES20.glUniform1f(muInfluence,0.0f);//195493	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195494	
{float value[]={-2.704262f, 0.0f, 5.6009107E-8f, 5.5083337E-8f, 0.0f, 1.5211474f, 0.0f, 0.0f, 2.364141E-7f, 0.0f, 0.6406695f, 0.6300799f, 1.3644304f, -1.0146023f, -0.7085412f, 1.2866414f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//195495	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195496	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195497	

GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195498	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195499	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195500	
GLES20.glUseProgram(mProgram2)        ;//195501	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195502	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195503	
GLES20.glUniform1i(mSampler,0)  ;//195504	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195505	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195506	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195507	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195508	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195509	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195510	
GLES20.glUniform1f(muAlpha, 0.009143527f)   ;//195511
GLES20.glUniform1f(muInfluence,0.0f);//195512	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195513	
{float value[]={-5.3999195f, 0.0f, 1.1184E-7f, 1.09991404E-7f, 0.0f, 3.0374546f, 0.0f, 0.0f, 4.7207595E-7f, 0.0f, 1.2793005f, 1.258155f, 2.3378654f, -0.7154785f, -0.4067933f, 1.5834017f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//195514	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195515	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195516	

GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195517	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195518	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195519	
GLES20.glUseProgram(mProgram2)        ;//195520	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195521	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195522	
GLES20.glUniform1i(mSampler,0)  ;//195523	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195524	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195525	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195526	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195527	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195528	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195529	
GLES20.glUniform1f(muAlpha, 0.062485732f)   ;//195530
GLES20.glUniform1f(muInfluence,0.0f);//195531	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195532	
{float value[]={-4.921385f, 0.0f, 1.0192887E-7f, 1.00244094E-7f, 0.0f, 2.768279f, 0.0f, 0.0f, 4.3024113E-7f, 0.0f, 1.1659304f, 1.1466588f, -0.6368395f, -0.7123382f, -0.71632874f, 1.2789826f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//195533	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195534	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195535	

GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195536	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195537	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195538	
GLES20.glUseProgram(mProgram2)        ;//195539	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195540	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195541	
GLES20.glUniform1i(mSampler,0)  ;//195542	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195543	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195544	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195545	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195546	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195547	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195548	
GLES20.glUniform1f(muAlpha, 0.2630213f);//195549
GLES20.glUniform1f(muInfluence,0.0f);//195550	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195551	
{float value[]={-5.9086285f, 0.0f, 1.2237608E-7f, 1.2035333E-7f, 0.0f, 3.3236034f, 0.0f, 0.0f, 5.165487E-7f, 0.0f, 1.3998191f, 1.3766817f, 1.8483113f, -1.2733526f, -0.87007695f, 1.1277757f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//195552	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195553	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195554	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195555	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195556	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195557	
GLES20.glUseProgram(mProgram2)        ;//195558	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195559	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195560	
GLES20.glUniform1i(mSampler,0)  ;//195561	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195562	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195563	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195564	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195565	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195566	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195567	
GLES20.glUniform1f(muAlpha, 0.08762257f)    ;//195568
GLES20.glUniform1f(muInfluence,0.0f);//195569	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195570	
{float value[]={-3.6110642f, 0.0f, 7.4790265E-8f, 7.355406E-8f, 0.0f, 2.0312235f, 0.0f, 0.0f, 3.1568925E-7f, 0.0f, 0.8555009f, 0.8413604f, -1.6936097f, -0.9446447f, -0.9700728f, 1.0294327f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//195571	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195572	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195573	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195574	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195575	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195576	
GLES20.glUseProgram(mProgram2)        ;//195577	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195578	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195579	
GLES20.glUniform1i(mSampler,0)  ;//195580	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195581	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195582	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195583	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195584	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195585	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195586	
GLES20.glUniform1f(muAlpha, 0.20171137f)    ;//195587
GLES20.glUniform1f(muInfluence,0.0f);//195588	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195589	
{float value[]={-6.3844686f, 0.0f, 1.322314E-7f, 1.3004576E-7f, 0.0f, 3.5912635f, 0.0f, 0.0f, 5.58148E-7f, 0.0f, 1.512551f, 1.4875501f, 1.1042104f, -1.2828814f, -0.82732713f, 1.1698189f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//195590	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195591	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195592	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195593	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195594	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195595	
GLES20.glUseProgram(mProgram2)        ;//195596	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195597	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195598	
GLES20.glUniform1i(mSampler,0)  ;//195599	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195600	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195601	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195602	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195603	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195604	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195605	
GLES20.glUniform1f(muAlpha, 0.17613323f)    ;//195606
GLES20.glUniform1f(muInfluence,0.0f);//195607	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195608	
{float value[]={-4.9445367f, 0.0f, 1.0240837E-7f, 1.00715674E-7f, 0.0f, 2.7813017f, 0.0f, 0.0f, 4.3226513E-7f, 0.0f, 1.1714152f, 1.152053f, -0.623665f, -1.0593337f, -0.5756321f, 1.4173536f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//195609	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195610	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195611	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195612	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195613	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195614	
GLES20.glUseProgram(mProgram2)        ;//195615	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195616	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195617	
GLES20.glUniform1i(mSampler,0)  ;//195618	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195619	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195620	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195621	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195622	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195623	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195624	
GLES20.glUniform1f(muAlpha, 0.13819848f)    ;//195625
GLES20.glUniform1f(muInfluence,0.0f);//195626	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195627	
{float value[]={-5.347296f, 0.0f, 1.107501E-7f, 1.0891952E-7f, 0.0f, 3.0078542f, 0.0f, 0.0f, 4.674755E-7f, 0.0f, 1.2668334f, 1.2458941f, -0.34178036f, -1.0104046f, -0.5494008f, 1.4431514f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//195628	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195629	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195630	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195631	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195632	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195633	
GLES20.glUseProgram(mProgram2)        ;//195634	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195635	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195636	
GLES20.glUniform1i(mSampler,0)  ;//195637	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195638	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195639	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195640	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195641	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195642	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195643	
GLES20.glUniform1f(muAlpha, 0.033225294f)   ;//195644
GLES20.glUniform1f(muInfluence,0.0f);//195645	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195646	
{float value[]={-4.792669f, 0.0f, 9.926298E-8f, 9.762227E-8f, 0.0f, 2.6958764f, 0.0f, 0.0f, 4.1898844E-7f, 0.0f, 1.1354362f, 1.1166686f, 0.2155473f, -0.63557583f, -0.60786486f, 1.3856536f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//195647	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195648	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195649	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195650	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195651	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195652	
GLES20.glUseProgram(mProgram2)        ;//195653	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195654	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195655	
GLES20.glUniform1i(mSampler,0)  ;//195656	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195657	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195658	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195659	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195660	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195661	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195662	
GLES20.glUniform1f(muAlpha, 0.13252299f)    ;//195663
GLES20.glUniform1f(muInfluence,0.0f);//195664	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195665	
{float value[]={-5.442039f, 0.0f, 1.1271236E-7f, 1.1084934E-7f, 0.0f, 3.061147f, 0.0f, 0.0f, 4.7575816E-7f, 0.0f, 1.2892791f, 1.2679687f, 1.7858708f, -1.1731622f, -0.4813618f, 1.5100658f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//195666	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195667	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195668	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195669	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195670	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195671	
GLES20.glUseProgram(mProgram2)        ;//195672	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195673	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195674	
GLES20.glUniform1i(mSampler,0)  ;//195675	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195676	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195677	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195678	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195679	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195680	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195681	
GLES20.glUniform1f(muAlpha, 0.13240615f)    ;//195682
GLES20.glUniform1f(muInfluence,0.0f);//195683	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195684	
{float value[]={-5.6196833f, 0.0f, 1.16391625E-7f, 1.14467795E-7f, 0.0f, 3.161072f, 0.0f, 0.0f, 4.9128835E-7f, 0.0f, 1.331365f, 1.309359f, -0.26828724f, -0.9716191f, -0.8178932f, 1.1790969f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//195685	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195686	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195687	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195688	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195689	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195690	
GLES20.glUseProgram(mProgram2)        ;//195691	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195692	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195693	
GLES20.glUniform1i(mSampler,0)  ;//195694	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195695	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195696	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195697	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195698	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195699	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195700	
GLES20.glUniform1f(muAlpha, 0.1409756f);//195701
GLES20.glUniform1f(muInfluence,0.0f);//195702	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195703	
{float value[]={-3.9892204f, 0.0f, 8.262242E-8f, 8.125676E-8f, 0.0f, 2.2439365f, 0.0f, 0.0f, 3.4874873E-7f, 0.0f, 0.9450903f, 0.929469f, -2.0451043f, -1.1283842f, -0.85666174f, 1.1409692f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//195704	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195705	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195706	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195707	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195708	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195709	
GLES20.glUseProgram(mProgram2)        ;//195710	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195711	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195712	
GLES20.glUniform1i(mSampler,0)  ;//195713	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195714	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195715	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195716	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195717	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195718	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195719	
GLES20.glUniform1f(muAlpha, 0.087053806f)   ;//195720
GLES20.glUniform1f(muInfluence,0.0f);//195721	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195722	
{float value[]={-6.2694225f, 0.0f, 1.2984864E-7f, 1.2770238E-7f, 0.0f, 3.52655f, 0.0f, 0.0f, 5.480903E-7f, 0.0f, 1.4852953f, 1.460745f, -1.7442832f, -0.5542459f, -0.3574301f, 1.631949f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)    ;//195723	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195724	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195725	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195726	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195727	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195728	
GLES20.glUseProgram(mProgram2)        ;//195729	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195730	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195731	
GLES20.glUniform1i(mSampler,0)  ;//195732	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195733	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195734	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195735	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195736	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195737	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195738	
GLES20.glUniform1f(muAlpha, 0.10621458f)    ;//195739
GLES20.glUniform1f(muInfluence,0.0f);//195740	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195741	
{float value[]={-6.0585814f, 0.0f, 1.2548182E-7f, 1.2340774E-7f, 0.0f, 3.407952f, 0.0f, 0.0f, 5.29658E-7f, 0.0f, 1.4353447f, 1.41162f, 1.0842477f, -0.6546675f, -0.48547047f, 1.5060251f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)    ;//195742	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195743	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195744	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195745	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195746	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195747	
GLES20.glUseProgram(mProgram2)        ;//195748	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195749	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195750	
GLES20.glUniform1i(mSampler,0)  ;//195751	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195752	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195753	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195754	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195755	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195756	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195757	
GLES20.glUniform1f(muAlpha, 0.083866104f)   ;//195758
GLES20.glUniform1f(muInfluence,0.0f);//195759	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195760	
{float value[]={-4.1616697f, 0.0f, 8.619409E-8f, 8.47694E-8f, 0.0f, 2.3409393f, 0.0f, 0.0f, 3.6382474E-7f, 0.0f, 0.98594546f, 0.96964884f, -0.4510988f, -1.6838006f, -0.9052209f, 1.0932126f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//195761	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195762	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195763	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195764	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195765	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195766	
GLES20.glUseProgram(mProgram2)        ;//195767	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195768	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195769	
GLES20.glUniform1i(mSampler,0)  ;//195770	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195771	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195772	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195773	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195774	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195775	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195776	
GLES20.glUniform1f(muAlpha, 0.06350184f)    ;//195777
GLES20.glUniform1f(muInfluence,0.0f);//195778	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195779	
{float value[]={-5.5194063f, 0.0f, 1.1431474E-7f, 1.1242524E-7f, 0.0f, 3.104666f, 0.0f, 0.0f, 4.8252184E-7f, 0.0f, 1.3076082f, 1.2859949f, 0.39781964f, -1.0951884f, -0.8032664f, 1.1934819f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//195780	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195781	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195782	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195783	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195784	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195785	
GLES20.glUseProgram(mProgram2)        ;//195786	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195787	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195788	
GLES20.glUniform1i(mSampler,0)  ;//195789	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195790	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195791	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195792	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195793	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195794	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195795	
GLES20.glUniform1f(muAlpha, 0.053768713f)   ;//195796
GLES20.glUniform1f(muInfluence,0.0f);//195797	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195798	
{float value[]={-2.594712f, 0.0f, 5.374017E-8f, 5.2851902E-8f, 0.0f, 1.4595256f, 0.0f, 0.0f, 2.2683693E-7f, 0.0f, 0.6147159f, 0.6045553f, 0.89745814f, -1.7449183f, -0.9148724f, 1.0837207f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//195799	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195800	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195801	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195802	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195803	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195804	
GLES20.glUseProgram(mProgram2)        ;//195805	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195806	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195807	
GLES20.glUniform1i(mSampler,0)  ;//195808	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195809	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195810	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195811	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195812	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195813	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195814	
GLES20.glUniform1f(muAlpha, 0.09670098f)    ;//195815
GLES20.glUniform1f(muInfluence,0.0f);//195816	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195817	
{float value[]={-2.3964262f, 0.0f, 4.963339E-8f, 4.8813003E-8f, 0.0f, 1.3479898f, 0.0f, 0.0f, 2.0950223E-7f, 0.0f, 0.56773984f, 0.5583557f, -0.7864393f, -1.284014f, -0.89747274f, 1.1008327f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//195818	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195819	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195820	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195821	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195822	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195823	
GLES20.glUseProgram(mProgram2)        ;//195824	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195825	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195826	
GLES20.glUniform1i(mSampler,0)  ;//195827	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195828	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195829	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195830	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195831	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195832	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195833	
GLES20.glUniform1f(muAlpha, 0.05989486f)    ;//195834
GLES20.glUniform1f(muInfluence,0.0f);//195835	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195836	
{float value[]={-2.455189f, 0.0f, 5.0850453E-8f, 5.0009948E-8f, 0.0f, 1.3810438f, 0.0f, 0.0f, 2.1463944E-7f, 0.0f, 0.58166134f, 0.5720471f, -2.0885897f, -1.2933292f, -0.9423803f, 1.0566674f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//195837	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195838	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195839	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195840	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195841	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195842	
GLES20.glUseProgram(mProgram2)        ;//195843	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195844	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195845	
GLES20.glUniform1i(mSampler,0)  ;//195846	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195847	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195848	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195849	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195850	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195851	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195852	
GLES20.glUniform1f(muAlpha, 0.06960367f)    ;//195853
GLES20.glUniform1f(muInfluence,0.0f);//195854	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195855	
{float value[]={-5.3817625f, 0.0f, 1.1146394E-7f, 1.0962156E-7f, 0.0f, 3.0272412f, 0.0f, 0.0f, 4.704886E-7f, 0.0f, 1.2749989f, 1.2539245f, -0.2591625f, -0.8737411f, -0.36640579f, 1.6231217f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//195856	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195857	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195858	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195859	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195860	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195861	
GLES20.glUseProgram(mProgram2)        ;//195862	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195863	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195864	
GLES20.glUniform1i(mSampler,0)  ;//195865	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195866	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195867	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195868	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195869	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195870	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195871	
GLES20.glUniform1f(muAlpha, 0.098293126f)   ;//195872
GLES20.glUniform1f(muInfluence,0.0f);//195873	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195874	
{float value[]={-3.523779f, 0.0f, 7.2982466E-8f, 7.177614E-8f, 0.0f, 1.9821255f, 0.0f, 0.0f, 3.0805853E-7f, 0.0f, 0.83482206f, 0.82102334f, -0.33023858f, -2.035648f, -0.9446986f, 1.0543875f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//195875	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195876	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195877	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195878	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195879	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195880	
GLES20.glUseProgram(mProgram2)        ;//195881	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195882	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195883	
GLES20.glUniform1i(mSampler,0)  ;//195884	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195885	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195886	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195887	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195888	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195889	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195890	
GLES20.glUniform1f(muAlpha, 0.048699226f)   ;//195891
GLES20.glUniform1f(muInfluence,0.0f);//195892	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195893	
{float value[]={-3.5294998f, 0.0f, 7.310095E-8f, 7.189267E-8f, 0.0f, 1.9853436f, 0.0f, 0.0f, 3.0855867E-7f, 0.0f, 0.8361774f, 0.8223563f, 0.28764144f, -1.4592603f, -0.37016642f, 1.6194232f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//195894	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195895	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195896	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195897	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195898	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195899	
GLES20.glUseProgram(mProgram2)        ;//195900	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195901	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195902	
GLES20.glUniform1i(mSampler,0)  ;//195903	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195904	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195905	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195906	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195907	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195908	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195909	
GLES20.glUniform1f(muAlpha, 0.03185607f)    ;//195910
GLES20.glUniform1f(muInfluence,0.0f);//195911	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195912	
{float value[]={-4.273367f, 0.0f, 8.8507505E-8f, 8.704457E-8f, 0.0f, 2.403769f, 0.0f, 0.0f, 3.7358964E-7f, 0.0f, 1.0124078f, 0.9956738f, -2.4461222f, -1.1648847f, -0.5901118f, 1.4031134f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//195913	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195914	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195915	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195916	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195917	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195918	
GLES20.glUseProgram(mProgram2)        ;//195919	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195920	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195921	
GLES20.glUniform1i(mSampler,0)  ;//195922	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195923	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195924	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195925	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195926	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195927	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195928	
GLES20.glUniform1f(muAlpha, 0.06301964f)    ;//195929
GLES20.glUniform1f(muInfluence,0.0f);//195930	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195931	
{float value[]={-4.3430424f, 0.0f, 8.995058E-8f, 8.846379E-8f, 0.0f, 2.4429615f, 0.0f, 0.0f, 3.7968084E-7f, 0.0f, 1.0289147f, 1.0119078f, -0.7561635f, -1.1808741f, -0.56482255f, 1.4279845f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//195932	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195933	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195934	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195935	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195936	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195937	
GLES20.glUseProgram(mProgram2)        ;//195938	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195939	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195940	
GLES20.glUniform1i(mSampler,0)  ;//195941	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195942	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195943	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195944	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195945	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195946	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195947	
GLES20.glUniform1f(muAlpha, 0.052278396f)   ;//195948
GLES20.glUniform1f(muInfluence,0.0f);//195949	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195950	
{float value[]={-4.9469132f, 0.0f, 1.024576E-7f, 1.0076408E-7f, 0.0f, 2.7826385f, 0.0f, 0.0f, 4.324729E-7f, 0.0f, 1.1719782f, 1.1526067f, -1.4647918f, -1.3173944f, -0.7695464f, 1.2266445f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//195951	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195952	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195953	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195954	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195955	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195956	
GLES20.glUseProgram(mProgram2)        ;//195957	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195958	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195959	
GLES20.glUniform1i(mSampler,0)  ;//195960	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195961	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195962	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195963	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195964	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195965	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195966	
GLES20.glUniform1f(muAlpha, 0.06407753f)    ;//195967
GLES20.glUniform1f(muInfluence,0.0f);//195968	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195969	
{float value[]={-6.185081f, 0.0f, 1.2810182E-7f, 1.2598443E-7f, 0.0f, 3.479108f, 0.0f, 0.0f, 5.40717E-7f, 0.0f, 1.4653139f, 1.4410938f, -1.4597706f, -1.508658f, -0.9465922f, 1.0525252f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)    ;//195970	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195971	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195972	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195973	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195974	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195975	
GLES20.glUseProgram(mProgram2)        ;//195976	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195977	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195978	
GLES20.glUniform1i(mSampler,0)  ;//195979	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195980	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//195981	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//195982	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//195983	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//195984	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//195985	
GLES20.glUniform1f(muAlpha, 0.03788286f)    ;//195986
GLES20.glUniform1f(muInfluence,0.0f);//195987	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195988	
{float value[]={-4.461507f, 0.0f, 9.2404136E-8f, 9.0876796E-8f, 0.0f, 2.5095975f, 0.0f, 0.0f, 3.900373E-7f, 0.0f, 1.0569801f, 1.0395094f, -0.09472076f, -1.255005f, -0.641624f, 1.3524525f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//195989	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//195990	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//195991	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//195992	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//195993	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//195994	
GLES20.glUseProgram(mProgram2)        ;//195995	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//195996	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//195997	
GLES20.glUniform1i(mSampler,0)  ;//195998	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//195999	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196000	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196001	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196002	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196003	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196004	
GLES20.glUniform1f(muAlpha, 0.030334948f)   ;//196005
GLES20.glUniform1f(muInfluence,0.0f);//196006	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196007	
{float value[]={-4.5295186f, 0.0f, 9.381276E-8f, 9.2262134E-8f, 0.0f, 2.5478542f, 0.0f, 0.0f, 3.9598308E-7f, 0.0f, 1.0730928f, 1.0553558f, -0.4295949f, -1.5015948f, -0.88585997f, 1.1122535f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//196008	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196009	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196010	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196011	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196012	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196013	
GLES20.glUseProgram(mProgram2)        ;//196014	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196015	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196016	
GLES20.glUniform1i(mSampler,0)  ;//196017	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196018	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196019	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196020	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196021	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196022	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196023	
GLES20.glUniform1f(muAlpha, 0.017670825f)   ;//196024
GLES20.glUniform1f(muInfluence,0.0f);//196025	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196026	
{float value[]={-4.211106f, 0.0f, 8.7217984E-8f, 8.5776364E-8f, 0.0f, 2.3687472f, 0.0f, 0.0f, 3.6814657E-7f, 0.0f, 0.9976575f, 0.98116726f, -0.75089306f, -2.1457195f, -0.8783734f, 1.1196164f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//196027	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196028	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196029	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196030	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196031	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196032	
GLES20.glUseProgram(mProgram2)        ;//196033	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196034	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196035	
GLES20.glUniform1i(mSampler,0)  ;//196036	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196037	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196038	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196039	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196040	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196041	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196042	
GLES20.glUniform1f(muAlpha, 0.0f);//196043	
GLES20.glUniform1f(muInfluence,0.0f);//196044	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196045	
{float value[]={-2.400955f, 0.0f, 4.972719E-8f, 4.8905253E-8f, 0.0f, 1.3505372f, 0.0f, 0.0f, 2.0989816E-7f, 0.0f, 0.5688127f, 0.55941087f, 0.8017695f, -1.287189f, -0.61080265f, 1.3827645f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196046	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196047	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196048	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196049	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196050	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196051	
GLES20.glUseProgram(mProgram2)        ;//196052	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196053	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196054	
GLES20.glUniform1i(mSampler,0)  ;//196055	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196056	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196057	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196058	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196059	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196060	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196061	
GLES20.glUniform1f(muAlpha, 0.0f);//196062	
GLES20.glUniform1f(muInfluence,0.0f);//196063	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196064	
{float value[]={-3.5885818f, 0.0f, 7.4324625E-8f, 7.309612E-8f, 0.0f, 2.0185773f, 0.0f, 0.0f, 3.1372377E-7f, 0.0f, 0.8501746f, 0.83612216f, -0.5659288f, -2.3314667f, -0.92954993f, 1.0692858f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//196065	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196066	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196067	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196068	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196069	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196070	
GLES20.glUseProgram(mProgram2)        ;//196071	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196072	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196073	
GLES20.glUniform1i(mSampler,0)  ;//196074	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196075	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196076	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196077	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196078	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196079	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196080	
GLES20.glUniform1f(muAlpha, 0.0f);//196081	
GLES20.glUniform1f(muInfluence,0.0f);//196082	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196083	
{float value[]={-4.150712f, 0.0f, 8.5967145E-8f, 8.45462E-8f, 0.0f, 2.3347757f, 0.0f, 0.0f, 3.6286679E-7f, 0.0f, 0.9833495f, 0.9670958f, -1.5765293f, -1.9863242f, -0.5623602f, 1.4304062f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196084	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196085	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196086	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196087	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196088	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196089	
GLES20.glUseProgram(mProgram2)        ;//196090	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196091	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196092	
GLES20.glUniform1i(mSampler,0)  ;//196093	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196094	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196095	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196096	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196097	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196098	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196099	
GLES20.glUniform1f(muAlpha, 0.0f);//196100	
GLES20.glUniform1f(muInfluence,0.0f);//196101	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196102	
{float value[]={-4.0424595f, 0.0f, 8.372508E-8f, 8.234119E-8f, 0.0f, 2.2738833f, 0.0f, 0.0f, 3.5340304E-7f, 0.0f, 0.9577032f, 0.9418734f, -0.16681656f, -1.6156343f, -0.86262745f, 1.135102f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196103	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196104	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196105	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196106	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196107	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196108	
GLES20.glUseProgram(mProgram2)        ;//196109	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196110	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196111	
GLES20.glUniform1i(mSampler,0)  ;//196112	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196113	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196114	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196115	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196116	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196117	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196118	
GLES20.glUniform1f(muAlpha, 0.0f);//196119	
GLES20.glUniform1f(muInfluence,0.0f);//196120	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196121	
{float value[]={-3.0232947f, 0.0f, 6.261672E-8f, 6.158174E-8f, 0.0f, 1.7006034f, 0.0f, 0.0f, 2.6430482E-7f, 0.0f, 0.71625185f, 0.704413f, -2.090995f, -1.473661f, -0.7315135f, 1.2640488f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//196122	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196123	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196124	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196125	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196126	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196127	
GLES20.glUseProgram(mProgram2)        ;//196128	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196129	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196130	
GLES20.glUniform1i(mSampler,0)  ;//196131	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196132	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196133	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196134	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196135	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196136	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196137	
GLES20.glUniform1f(muAlpha, 0.0f);//196138	
GLES20.glUniform1f(muInfluence,0.0f);//196139	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196140	
{float value[]={-3.6142788f, 0.0f, 7.485684E-8f, 7.361954E-8f, 0.0f, 2.0330317f, 0.0f, 0.0f, 3.1597028E-7f, 0.0f, 0.8562625f, 0.8421094f, 0.4186643f, -1.2310399f, -0.47975153f, 1.5116494f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196141	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196142	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196143	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196144	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196145	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196146	
GLES20.glUseProgram(mProgram2)        ;//196147	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196148	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196149	
GLES20.glUniform1i(mSampler,0)  ;//196150	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196151	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196152	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196153	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196154	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196155	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196156	
GLES20.glUniform1f(muAlpha, 0.0f);//196157	
GLES20.glUniform1f(muInfluence,0.0f);//196158	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196159	
{float value[]={-6.3239965f, 0.0f, 1.3097895E-7f, 1.2881401E-7f, 0.0f, 3.5572479f, 0.0f, 0.0f, 5.5286137E-7f, 0.0f, 1.4982245f, 1.4734604f, 2.1201174f, -1.7868905f, -0.44726408f, 1.5435998f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//196160	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196161	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196162	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196163	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196164	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196165	
GLES20.glUseProgram(mProgram2)        ;//196166	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196167	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196168	
GLES20.glUniform1i(mSampler,0)  ;//196169	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196170	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196171	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196172	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196173	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196174	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196175	
GLES20.glUniform1f(muAlpha, 0.21397206f)    ;//196176
GLES20.glUniform1f(muInfluence,0.0f);//196177	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196178	
{float value[]={-3.726449f, 0.0f, 7.718005E-8f, 7.5904346E-8f, 0.0f, 2.0961275f, 0.0f, 0.0f, 3.257765E-7f, 0.0f, 0.8828369f, 0.8682445f, 1.0926545f, 1.7348595f, -0.3664838f, 1.623045f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196179	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196180	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196181	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196182	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196183	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196184	
GLES20.glUseProgram(mProgram2)        ;//196185	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196186	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196187	
GLES20.glUniform1i(mSampler,0)  ;//196188	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196189	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196190	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196191	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196192	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196193	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196194	
GLES20.glUniform1f(muAlpha, 1.0907131f);//196195
GLES20.glUniform1f(muInfluence,0.0f);//196196	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196197	
{float value[]={-3.627587f, 0.0f, 7.513248E-8f, 7.3890625E-8f, 0.0f, 2.0405178f, 0.0f, 0.0f, 3.1713375E-7f, 0.0f, 0.8594154f, 0.8452102f, -1.6543354f, 1.222886f, -0.8209008f, 1.176139f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)    ;//196198	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196199	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196200	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196201	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196202	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196203	
GLES20.glUseProgram(mProgram2)        ;//196204	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196205	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196206	
GLES20.glUniform1i(mSampler,0)  ;//196207	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196208	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196209	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196210	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196211	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196212	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196213	
GLES20.glUniform1f(muAlpha, 0.9030114f);//196214
GLES20.glUniform1f(muInfluence,0.0f);//196215	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196216	
{float value[]={-3.4663203f, 0.0f, 7.1792414E-8f, 7.0605765E-8f, 0.0f, 1.9498051f, 0.0f, 0.0f, 3.0303536E-7f, 0.0f, 0.8212095f, 0.8076358f, 0.57946706f, 1.2101527f, -0.791271f, 1.2052791f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196217	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196218	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196219	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196220	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196221	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196222	
GLES20.glUseProgram(mProgram2)        ;//196223	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196224	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196225	
GLES20.glUniform1i(mSampler,0)  ;//196226	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196227	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196228	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196229	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196230	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196231	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196232	
GLES20.glUniform1f(muAlpha, 0.8272965f);//196233
GLES20.glUniform1f(muInfluence,0.0f);//196234	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196235	
{float value[]={-5.72721f, 0.0f, 1.18618644E-7f, 1.1665801E-7f, 0.0f, 3.2215557f, 0.0f, 0.0f, 5.006886E-7f, 0.0f, 1.3568392f, 1.3344121f, -1.3320563f, 1.2458053f, -0.6611387f, 1.3332604f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196236	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196237	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196238	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196239	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196240	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196241	
GLES20.glUseProgram(mProgram2)        ;//196242	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196243	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196244	
GLES20.glUniform1i(mSampler,0)  ;//196245	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196246	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196247	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196248	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196249	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196250	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196251	
GLES20.glUniform1f(muAlpha, 0.73757786f)    ;//196252
GLES20.glUniform1f(muInfluence,0.0f);//196253	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196254	
{float value[]={-2.2096784f, 0.0f, 4.5765578E-8f, 4.5009124E-8f, 0.0f, 1.2429441f, 0.0f, 0.0f, 1.9317623E-7f, 0.0f, 0.5234972f, 0.51484436f, 1.5083624f, 1.1868657f, -0.7039317f, 1.2911748f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196255	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196256	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196257	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196258	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196259	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196260	
GLES20.glUseProgram(mProgram2)        ;//196261	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196262	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196263	
GLES20.glUniform1i(mSampler,0)  ;//196264	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196265	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196266	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196267	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196268	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196269	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196270	
GLES20.glUniform1f(muAlpha, 0.5776904f);//196271
GLES20.glUniform1f(muInfluence,0.0f);//196272	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196273	
{float value[]={-3.3008595f, 0.0f, 6.836548E-8f, 6.7235476E-8f, 0.0f, 1.8567334f, 0.0f, 0.0f, 2.8857028E-7f, 0.0f, 0.78201f, 0.7690842f, -1.4466846f, 1.2346501f, -0.5965346f, 1.3967967f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//196274	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196275	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196276	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196277	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196278	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196279	
GLES20.glUseProgram(mProgram2)        ;//196280	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196281	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196282	
GLES20.glUniform1i(mSampler,0)  ;//196283	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196284	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196285	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196286	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196287	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196288	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196289	
GLES20.glUniform1f(muAlpha, 0.77220464f)    ;//196290
GLES20.glUniform1f(muInfluence,0.0f);//196291	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196292	
{float value[]={-2.9464712f, 0.0f, 6.10256E-8f, 6.0016916E-8f, 0.0f, 1.6573901f, 0.0f, 0.0f, 2.575887E-7f, 0.0f, 0.6980515f, 0.6865135f, 1.2234628f, 1.3437438f, -0.4863823f, 1.5051283f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)    ;//196293	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196294	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196295	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196296	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196297	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196298	
GLES20.glUseProgram(mProgram2)        ;//196299	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196300	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196301	
GLES20.glUniform1i(mSampler,0)  ;//196302	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196303	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196304	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196305	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196306	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196307	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196308	
GLES20.glUniform1f(muAlpha, 0.06254935f)    ;//196309
GLES20.glUniform1f(muInfluence,0.0f);//196310	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196311	
{float value[]={-2.9618034f, 0.0f, 6.134316E-8f, 6.032922E-8f, 0.0f, 1.6660144f, 0.0f, 0.0f, 2.589291E-7f, 0.0f, 0.70168394f, 0.6900858f, -0.42710093f, 0.8544474f, -0.9669202f, 1.0325332f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196312	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196313	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196314	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196315	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196316	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196317	
GLES20.glUseProgram(mProgram2)        ;//196318	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196319	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196320	
GLES20.glUniform1i(mSampler,0)  ;//196321	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196322	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196323	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196324	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196325	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196326	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196327	
GLES20.glUniform1f(muAlpha, 0.29369098f)    ;//196328
GLES20.glUniform1f(muInfluence,0.0f);//196329	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196330	
{float value[]={-3.472362f, 0.0f, 7.191754E-8f, 7.0728824E-8f, 0.0f, 1.9532036f, 0.0f, 0.0f, 3.0356352E-7f, 0.0f, 0.82264084f, 0.80904347f, 0.9273309f, 0.8025028f, -0.9061512f, 1.0922977f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196331	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196332	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196333	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196334	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196335	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196336	
GLES20.glUseProgram(mProgram2)        ;//196337	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196338	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196339	
GLES20.glUniform1i(mSampler,0)  ;//196340	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196341	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196342	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196343	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196344	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196345	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196346	
GLES20.glUniform1f(muAlpha, 0.9282499f);//196347
GLES20.glUniform1f(muInfluence,0.0f);//196348	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196349	
{float value[]={-6.2058177f, 0.0f, 1.2853128E-7f, 1.264068E-7f, 0.0f, 3.4907725f, 0.0f, 0.0f, 5.425298E-7f, 0.0f, 1.4702266f, 1.4459254f, 0.87766373f, 0.7197689f, -0.9706625f, 1.0288527f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196350	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196351	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196352	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196353	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196354	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196355	
GLES20.glUseProgram(mProgram2)        ;//196356	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196357	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196358	
GLES20.glUniform1i(mSampler,0)  ;//196359	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196360	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196361	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196362	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196363	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196364	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196365	
GLES20.glUniform1f(muAlpha, 0.9031905f);//196366
GLES20.glUniform1f(muInfluence,0.0f);//196367	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196368	
{float value[]={-5.368183f, 0.0f, 1.1118269E-7f, 1.0934496E-7f, 0.0f, 3.019603f, 0.0f, 0.0f, 4.6930145E-7f, 0.0f, 1.2717817f, 1.2507606f, -0.18039154f, 1.2532508f, -0.51865625f, 1.4733877f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196369	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196370	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196371	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196372	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196373	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196374	
GLES20.glUseProgram(mProgram2)        ;//196375	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196376	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196377	
GLES20.glUniform1i(mSampler,0)  ;//196378	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196379	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196380	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196381	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196382	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196383	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196384	
GLES20.glUniform1f(muAlpha, 0.71087974f)    ;//196385
GLES20.glUniform1f(muInfluence,0.0f);//196386	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196387	
{float value[]={-6.317045f, 0.0f, 1.3083498E-7f, 1.2867241E-7f, 0.0f, 3.553338f, 0.0f, 0.0f, 5.522536E-7f, 0.0f, 1.4965776f, 1.4718409f, -0.5189864f, 1.1554247f, -0.5290933f, 1.4631232f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//196388	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196389	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196390	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196391	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196392	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196393	
GLES20.glUseProgram(mProgram2)        ;//196394	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196395	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196396	
GLES20.glUniform1i(mSampler,0)  ;//196397	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196398	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196399	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196400	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196401	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196402	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196403	
GLES20.glUniform1f(muAlpha, 0.74244285f)    ;//196404
GLES20.glUniform1f(muInfluence,0.0f);//196405	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196406	
{float value[]={-3.9562087f, 0.0f, 8.19387E-8f, 8.058434E-8f, 0.0f, 2.2253675f, 0.0f, 0.0f, 3.4586276E-7f, 0.0f, 0.9372695f, 0.9217774f, 0.6506373f, 0.94779027f, -0.76195264f, 1.2341129f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196407	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196408	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196409	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196410	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196411	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196412	
GLES20.glUseProgram(mProgram2)        ;//196413	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196414	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196415	
GLES20.glUniform1i(mSampler,0)  ;//196416	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196417	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196418	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196419	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196420	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196421	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196422	
GLES20.glUniform1f(muAlpha, 0.16224459f)    ;//196423
GLES20.glUniform1f(muInfluence,0.0f);//196424	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196425	
{float value[]={-5.571315f, 0.0f, 1.1538984E-7f, 1.1348257E-7f, 0.0f, 3.1338646f, 0.0f, 0.0f, 4.870598E-7f, 0.0f, 1.3199059f, 1.2980893f, -0.49257448f, 0.9171572f, -0.5676948f, 1.4251598f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196426	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196427	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196428	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196429	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196430	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196431	
GLES20.glUseProgram(mProgram2)        ;//196432	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196433	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196434	
GLES20.glUniform1i(mSampler,0)  ;//196435	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196436	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196437	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196438	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196439	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196440	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196441	
GLES20.glUniform1f(muAlpha, 0.80932313f)    ;//196442
GLES20.glUniform1f(muInfluence,0.0f);//196443	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196444	
{float value[]={-4.1822267f, 0.0f, 8.6619856E-8f, 8.518812E-8f, 0.0f, 2.3525026f, 0.0f, 0.0f, 3.6562187E-7f, 0.0f, 0.9908156f, 0.9744385f, 0.46172222f, 0.7438322f, -0.68576026f, 1.3090458f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196445	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196446	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196447	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196448	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196449	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196450	
GLES20.glUseProgram(mProgram2)        ;//196451	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196452	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196453	
GLES20.glUniform1i(mSampler,0)  ;//196454	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196455	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196456	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196457	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196458	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196459	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196460	
GLES20.glUniform1f(muAlpha, 0.70788175f)    ;//196461
GLES20.glUniform1f(muInfluence,0.0f);//196462	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196463	
{float value[]={-4.405768f, 0.0f, 9.124971E-8f, 8.974145E-8f, 0.0f, 2.4782445f, 0.0f, 0.0f, 3.8516447E-7f, 0.0f, 1.043775f, 1.0265225f, 0.016077936f, 0.71749103f, -0.73796237f, 1.2577065f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196464	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196465	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196466	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196467	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196468	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196469	
GLES20.glUseProgram(mProgram2)        ;//196470	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196471	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196472	
GLES20.glUniform1i(mSampler,0)  ;//196473	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196474	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196475	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196476	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196477	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196478	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196479	
GLES20.glUniform1f(muAlpha, 0.048699822f)   ;//196480
GLES20.glUniform1f(muInfluence,0.0f);//196481	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196482	
{float value[]={-5.485176f, 0.0f, 1.13605786E-7f, 1.11728006E-7f, 0.0f, 3.0854115f, 0.0f, 0.0f, 4.795293E-7f, 0.0f, 1.2994988f, 1.2780194f, 0.7011396f, 0.787623f, -0.64863694f, 1.3455555f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196483	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196484	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196485	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196486	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196487	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196488	
GLES20.glUseProgram(mProgram2)        ;//196489	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196490	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196491	
GLES20.glUniform1i(mSampler,0)  ;//196492	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196493	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196494	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196495	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196496	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196497	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196498	
GLES20.glUniform1f(muAlpha, 0.6641013f);//196499	
GLES20.glUniform1f(muInfluence,0.0f);//196500	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196501	
{float value[]={-2.6209154f, 0.0f, 5.428288E-8f, 5.338564E-8f, 0.0f, 1.4742649f, 0.0f, 0.0f, 2.2912769E-7f, 0.0f, 0.62092376f, 0.61066055f, -2.0312307f, 0.7822354f, -0.756161f, 1.2398087f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196502	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196503	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196504	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196505	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196506	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196507	
GLES20.glUseProgram(mProgram2)        ;//196508	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196509	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196510	
GLES20.glUniform1i(mSampler,0)  ;//196511	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196512	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196513	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196514	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196515	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196516	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196517	
GLES20.glUniform1f(muAlpha, 0.9321543f);//196518	
GLES20.glUniform1f(muInfluence,0.0f);//196519	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196520	
{float value[]={-3.944391f, 0.0f, 8.169394E-8f, 8.0343625E-8f, 0.0f, 2.21872f, 0.0f, 0.0f, 3.448296E-7f, 0.0f, 0.9344697f, 0.91902393f, 0.14108618f, 0.90804785f, -0.6218183f, 1.3719308f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//196521	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196522	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196523	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196524	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196525	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196526	
GLES20.glUseProgram(mProgram2)        ;//196527	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196528	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196529	
GLES20.glUniform1i(mSampler,0)  ;//196530	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196531	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196532	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196533	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196534	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196535	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196536	
GLES20.glUniform1f(muAlpha, 0.5769717f);//196537	
GLES20.glUniform1f(muInfluence,0.0f);//196538	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196539	
{float value[]={-2.6701548f, 0.0f, 5.5302696E-8f, 5.43886E-8f, 0.0f, 1.5019621f, 0.0f, 0.0f, 2.3343233E-7f, 0.0f, 0.6325891f, 0.6221331f, -0.027143808f, 0.6215466f, -0.742193f, 1.2535459f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196540	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196541	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196542	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196543	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196544	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196545	
GLES20.glUseProgram(mProgram2)        ;//196546	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196547	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196548	
GLES20.glUniform1i(mSampler,0)  ;//196549	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196550	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196551	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196552	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196553	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196554	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196555	
GLES20.glUniform1f(muAlpha, 0.22144201f)    ;//196556
GLES20.glUniform1f(muInfluence,0.0f);//196557	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196558	
{float value[]={-5.4825397f, 0.0f, 1.1355118E-7f, 1.11674304E-7f, 0.0f, 3.0839286f, 0.0f, 0.0f, 4.792989E-7f, 0.0f, 1.2988741f, 1.2774051f, -0.57708675f, 0.81510717f, -0.6316068f, 1.3623042f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//196559	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196560	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196561	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196562	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196563	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196564	
GLES20.glUseProgram(mProgram2)        ;//196565	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196566	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196567	
GLES20.glUniform1i(mSampler,0)  ;//196568	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196569	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196570	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196571	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196572	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196573	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196574	
GLES20.glUniform1f(muAlpha, 0.46618935f)    ;//196575	
GLES20.glUniform1f(muInfluence,0.0f);//196576	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196577	
{float value[]={-5.521674f, 0.0f, 1.1436171E-7f, 1.1247143E-7f, 0.0f, 3.1059415f, 0.0f, 0.0f, 4.8272005E-7f, 0.0f, 1.3081454f, 1.2865232f, -0.24459909f, 0.7929198f, -0.6304419f, 1.3634498f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196578	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196579	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196580	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196581	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196582	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196583	
GLES20.glUseProgram(mProgram2)        ;//196584	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196585	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196586	
GLES20.glUniform1i(mSampler,0)  ;//196587	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196588	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196589	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196590	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196591	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196592	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196593	
GLES20.glUniform1f(muAlpha, 0.5933195f);//196594	
GLES20.glUniform1f(muInfluence,0.0f);//196595	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196596	
{float value[]={-3.6454585f, 0.0f, 7.550262E-8f, 7.425464E-8f, 0.0f, 2.0505705f, 0.0f, 0.0f, 3.186961E-7f, 0.0f, 0.8636493f, 0.8493741f, -0.9786785f, 0.4340207f, -0.98347276f, 1.0162542f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196597	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196598	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196599	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196600	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196601	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196602	
GLES20.glUseProgram(mProgram2)        ;//196603	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196604	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196605	
GLES20.glUniform1i(mSampler,0)  ;//196606	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196607	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196608	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196609	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196610	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196611	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196612	
GLES20.glUniform1f(muAlpha, 0.84696364f)    ;//196613	
GLES20.glUniform1f(muInfluence,0.0f);//196614	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196615	
{float value[]={-4.5748305f, 0.0f, 9.4751236E-8f, 9.31851E-8f, 0.0f, 2.5733423f, 0.0f, 0.0f, 3.9994438E-7f, 0.0f, 1.0838279f, 1.0659133f, 1.6587468f, 0.6852814f, -0.6014631f, 1.3919497f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//196616	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196617	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196618	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196619	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196620	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196621	
GLES20.glUseProgram(mProgram2)        ;//196622	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196623	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196624	
GLES20.glUniform1i(mSampler,0)  ;//196625	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196626	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196627	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196628	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196629	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196630	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196631	
GLES20.glUniform1f(muAlpha, 0.63227725f)    ;//196632	
GLES20.glUniform1f(muInfluence,0.0f);//196633	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196634	
{float value[]={-3.1537404f, 0.0f, 6.531844E-8f, 6.42388E-8f, 0.0f, 1.773979f, 0.0f, 0.0f, 2.7570874E-7f, 0.0f, 0.74715585f, 0.7348062f, -1.1748077f, 0.70762473f, -0.60630405f, 1.3871887f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196635	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196636	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196637	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196638	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196639	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196640	
GLES20.glUseProgram(mProgram2)        ;//196641	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196642	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196643	
GLES20.glUniform1i(mSampler,0)  ;//196644	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196645	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196646	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196647	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196648	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196649	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196650	
GLES20.glUniform1f(muAlpha, 0.11252998f)    ;//196651	
GLES20.glUniform1f(muInfluence,0.0f);//196652	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196653	
{float value[]={-6.377945f, 0.0f, 1.320963E-7f, 1.2991289E-7f, 0.0f, 3.587594f, 0.0f, 0.0f, 5.5757766E-7f, 0.0f, 1.5110055f, 1.4860302f, -0.80095625f, 0.80449915f, -0.5179287f, 1.4741032f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196654	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196655	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196656	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196657	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196658	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196659	
GLES20.glUseProgram(mProgram2)        ;//196660	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196661	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196662	
GLES20.glUniform1i(mSampler,0)  ;//196663	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196664	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196665	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196666	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196667	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196668	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196669	
GLES20.glUniform1f(muAlpha, 0.70666564f)    ;//196670	
GLES20.glUniform1f(muInfluence,0.0f);//196671	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196672	
{float value[]={-3.8752222f, 0.0f, 8.026135E-8f, 7.893472E-8f, 0.0f, 2.1798124f, 0.0f, 0.0f, 3.3878268E-7f, 0.0f, 0.9180829f, 0.90290797f, 1.2593482f, 0.41496968f, -0.8658401f, 1.1319425f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196673	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196674	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196675	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196676	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196677	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196678	
GLES20.glUseProgram(mProgram2)        ;//196679	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196680	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196681	
GLES20.glUniform1i(mSampler,0)  ;//196682	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196683	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196684	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196685	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196686	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196687	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196688	
GLES20.glUniform1f(muAlpha, 0.77711385f)    ;//196689	
GLES20.glUniform1f(muInfluence,0.0f);//196690	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196691	
{float value[]={-3.9728992f, 0.0f, 8.228438E-8f, 8.092431E-8f, 0.0f, 2.2347558f, 0.0f, 0.0f, 3.4732187E-7f, 0.0f, 0.9412236f, 0.9256662f, -0.67931443f, 0.27698618f, -0.81562597f, 1.1813266f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//196692	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196693	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196694	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196695	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196696	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196697	
GLES20.glUseProgram(mProgram2)        ;//196698	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196699	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196700	
GLES20.glUniform1i(mSampler,0)  ;//196701	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196702	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196703	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196704	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196705	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196706	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196707	
GLES20.glUniform1f(muAlpha, 0.74849105f)    ;//196708	
GLES20.glUniform1f(muInfluence,0.0f);//196709	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196710	
{float value[]={-4.6381984f, 0.0f, 9.606368E-8f, 9.447585E-8f, 0.0f, 2.6089866f, 0.0f, 0.0f, 4.054842E-7f, 0.0f, 1.0988404f, 1.0806777f, 1.2810447f, 0.6979763f, -0.55884105f, 1.4338672f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//196711	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196712	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196713	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196714	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196715	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196716	
GLES20.glUseProgram(mProgram2)        ;//196717	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196718	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196719	
GLES20.glUniform1i(mSampler,0)  ;//196720	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196721	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196722	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196723	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196724	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196725	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196726	
GLES20.glUniform1f(muAlpha, 0.79333836f)    ;//196727	
GLES20.glUniform1f(muInfluence,0.0f);//196728	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196729	
{float value[]={-3.2983086f, 0.0f, 6.831266E-8f, 6.718352E-8f, 0.0f, 1.8552986f, 0.0f, 0.0f, 2.883473E-7f, 0.0f, 0.7814057f, 0.7684899f, -0.9204562f, 0.24382757f, -0.93635553f, 1.0625926f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//196730	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196731	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196732	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196733	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196734	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196735	
GLES20.glUseProgram(mProgram2)        ;//196736	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196737	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196738	
GLES20.glUniform1i(mSampler,0)  ;//196739	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196740	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196741	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196742	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196743	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196744	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196745	
GLES20.glUniform1f(muAlpha, 0.6295482f);//196746
GLES20.glUniform1f(muInfluence,0.0f);//196747	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196748	
{float value[]={-6.340548f, 0.0f, 1.3132176E-7f, 1.2915115E-7f, 0.0f, 3.5665581f, 0.0f, 0.0f, 5.543083E-7f, 0.0f, 1.5021458f, 1.4773169f, 1.4040103f, 0.85206586f, -0.36231804f, 1.627142f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196749	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196750	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196751	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196752	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196753	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196754	
GLES20.glUseProgram(mProgram2)        ;//196755	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196756	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196757	
GLES20.glUniform1i(mSampler,0)  ;//196758	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196759	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196760	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196761	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196762	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196763	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196764	
GLES20.glUniform1f(muAlpha, 0.73013854f)    ;//196765	
GLES20.glUniform1f(muInfluence,0.0f);//196766	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196767	
{float value[]={-5.865186f, 0.0f, 1.2147633E-7f, 1.1946845E-7f, 0.0f, 3.2991672f, 0.0f, 0.0f, 5.1275083E-7f, 0.0f, 1.3895272f, 1.3665599f, -1.535772f, 0.31127483f, -0.66341114f, 1.3310255f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196768	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196769	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196770	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196771	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196772	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196773	
GLES20.glUseProgram(mProgram2)        ;//196774	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196775	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196776	
GLES20.glUniform1i(mSampler,0)  ;//196777	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196778	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196779	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196780	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196781	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196782	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196783	
GLES20.glUniform1f(muAlpha, 0.23823588f)    ;//196784	
GLES20.glUniform1f(muInfluence,0.0f);//196785	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196786	
{float value[]={-4.0013504f, 0.0f, 8.2873655E-8f, 8.150384E-8f, 0.0f, 2.2507598f, 0.0f, 0.0f, 3.498092E-7f, 0.0f, 0.9479641f, 0.93229526f, 1.0469139f, 0.13166939f, -0.99014187f, 1.0096953f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196787	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196788	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196789	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196790	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196791	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196792	
GLES20.glUseProgram(mProgram2)        ;//196793	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196794	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196795	
GLES20.glUniform1i(mSampler,0)  ;//196796	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196797	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196798	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196799	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196800	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196801	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196802	
GLES20.glUniform1f(muAlpha, 0.5902031f);//196803	
GLES20.glUniform1f(muInfluence,0.0f);//196804	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196805	
{float value[]={-5.4745264f, 0.0f, 1.1338521E-7f, 1.1151108E-7f, 0.0f, 3.0794213f, 0.0f, 0.0f, 4.785983E-7f, 0.0f, 1.2969757f, 1.2755381f, 0.29921284f, 0.05024764f, -0.9946684f, 1.0052435f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196806	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196807	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196808	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196809	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196810	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196811	
GLES20.glUseProgram(mProgram2)        ;//196812	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196813	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196814	
GLES20.glUniform1i(mSampler,0)  ;//196815	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196816	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196817	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196818	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196819	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196820	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196821	
GLES20.glUniform1f(muAlpha, 0.40691665f)    ;//196822	
GLES20.glUniform1f(muInfluence,0.0f);//196823	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196824	
{float value[]={-5.747042f, 0.0f, 1.190294E-7f, 1.1706197E-7f, 0.0f, 3.232711f, 0.0f, 0.0f, 5.024224E-7f, 0.0f, 1.3615377f, 1.3390329f, 1.1001779f, 0.3279176f, -0.6872591f, 1.3075718f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196825	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196826	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196827	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196828	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196829	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196830	
GLES20.glUseProgram(mProgram2)        ;//196831	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196832	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196833	
GLES20.glUniform1i(mSampler,0)  ;//196834	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196835	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196836	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196837	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196838	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196839	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196840	
GLES20.glUniform1f(muAlpha, 0.1870213f);//196841	
GLES20.glUniform1f(muInfluence,0.0f);//196842	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196843	
{float value[]={-3.9877403f, 0.0f, 8.259176E-8f, 8.122661E-8f, 0.0f, 2.243104f, 0.0f, 0.0f, 3.4861932E-7f, 0.0f, 0.94473964f, 0.9291241f, 0.2928476f, 0.08677202f, -0.6573889f, 1.3369482f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196844	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196845	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196846	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196847	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196848	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196849	
GLES20.glUseProgram(mProgram2)        ;//196850	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196851	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196852	
GLES20.glUniform1i(mSampler,0)  ;//196853	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196854	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196855	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196856	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196857	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196858	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196859	
GLES20.glUniform1f(muAlpha, 0.54043144f)    ;//196860	
GLES20.glUniform1f(muInfluence,0.0f);//196861	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196862	
{float value[]={-4.289929f, 0.0f, 8.885053E-8f, 8.738192E-8f, 0.0f, 2.4130852f, 0.0f, 0.0f, 3.7503753E-7f, 0.0f, 1.0163316f, 0.99953264f, -2.2630472f, 0.51469547f, -0.49159032f, 1.5000063f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196863	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196864	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196865	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196866	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196867	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196868	
GLES20.glUseProgram(mProgram2)        ;//196869	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196870	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196871	
GLES20.glUniform1i(mSampler,0)  ;//196872	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196873	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196874	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196875	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196876	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196877	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196878	
GLES20.glUniform1f(muAlpha, 0.328143f) ;//196879	
GLES20.glUniform1f(muInfluence,0.0f);//196880	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196881	
{float value[]={-6.2257457f, 0.0f, 1.2894404E-7f, 1.2681274E-7f, 0.0f, 3.501982f, 0.0f, 0.0f, 5.4427204E-7f, 0.0f, 1.4749478f, 1.4505686f, -0.50569254f, 0.11830425f, -0.5848589f, 1.4082794f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//196882	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196883	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196884	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196885	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196886	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196887	
GLES20.glUseProgram(mProgram2)        ;//196888	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196889	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196890	
GLES20.glUniform1i(mSampler,0)  ;//196891	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196892	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196893	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196894	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196895	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196896	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196897	
GLES20.glUniform1f(muAlpha, 0.12844934f)    ;//196898	
GLES20.glUniform1f(muInfluence,0.0f);//196899	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196900	
{float value[]={-2.4586217f, 0.0f, 5.0921546E-8f, 5.007987E-8f, 0.0f, 1.3829747f, 0.0f, 0.0f, 2.1493955E-7f, 0.0f, 0.58247465f, 0.57284695f, 1.4370425f, -0.32666814f, -0.96420014f, 1.0352082f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)       ;//196901	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196902	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196903	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196904	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196905	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196906	
GLES20.glUseProgram(mProgram2)        ;//196907	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196908	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196909	
GLES20.glUniform1i(mSampler,0)  ;//196910	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196911	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196912	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196913	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196914	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196915	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196916	
GLES20.glUniform1f(muAlpha, 0.4487042f);//196917
GLES20.glUniform1f(muInfluence,0.0f);//196918	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196919	
{float value[]={-6.089745f, 0.0f, 1.2612726E-7f, 1.2404251E-7f, 0.0f, 3.4254818f, 0.0f, 0.0f, 5.323824E-7f, 0.0f, 1.4427278f, 1.418881f, 0.33814147f, 0.2600088f, -0.34400254f, 1.6451547f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196920	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196921	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196922	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196923	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196924	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196925	
GLES20.glUseProgram(mProgram2)        ;//196926	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196927	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196928	
GLES20.glUniform1i(mSampler,0)  ;//196929	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196930	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196931	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196932	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196933	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196934	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196935	
GLES20.glUniform1f(muAlpha, 0.20172663f)    ;//196936	
GLES20.glUniform1f(muInfluence,0.0f);//196937	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196938	
{float value[]={-6.105819f, 0.0f, 1.2646018E-7f, 1.2436993E-7f, 0.0f, 3.4345233f, 0.0f, 0.0f, 5.3378767E-7f, 0.0f, 1.446536f, 1.4226263f, 0.19286832f, 0.25070384f, -0.6816665f, 1.313072f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)  ;//196939	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196940	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196941	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196942	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196943	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196944	
GLES20.glUseProgram(mProgram2)        ;//196945	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196946	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196947	
GLES20.glUniform1i(mSampler,0)  ;//196948	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196949	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196950	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196951	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196952	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196953	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196954	
GLES20.glUniform1f(muAlpha, 0.0676617f);//196955	
GLES20.glUniform1f(muInfluence,0.0f);//196956	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196957	
{float value[]={-3.3208535f, 0.0f, 6.877959E-8f, 6.764274E-8f, 0.0f, 1.8679801f, 0.0f, 0.0f, 2.9031824E-7f, 0.0f, 0.7867468f, 0.77374274f, 0.054462824f, -0.39409885f, -0.9726451f, 1.0269029f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//196958	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196959	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196960	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196961	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196962	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196963	
GLES20.glUseProgram(mProgram2)        ;//196964	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196965	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196966	
GLES20.glUniform1i(mSampler,0)  ;//196967	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196968	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196969	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196970	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196971	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196972	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196973	
GLES20.glUniform1f(muAlpha, 0.20343925f)    ;//196974	
GLES20.glUniform1f(muInfluence,0.0f);//196975	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196976	
{float value[]={-3.8304706f, 0.0f, 7.9334484E-8f, 7.802317E-8f, 0.0f, 2.1546397f, 0.0f, 0.0f, 3.3487038E-7f, 0.0f, 0.9074807f, 0.892481f, -0.5420139f, 0.0013477113f, -0.6075902f, 1.3859237f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//196977	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196978	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196979	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196980	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//196981	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196982	
GLES20.glUseProgram(mProgram2)        ;//196983	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//196984	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//196985	
GLES20.glUniform1i(mSampler,0)  ;//196986	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//196987	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//196988	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//196989	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//196990	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//196991	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//196992	
GLES20.glUniform1f(muAlpha, 0.59534013f)    ;//196993	
GLES20.glUniform1f(muInfluence,0.0f);//196994	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//196995	
{float value[]={-6.2405934f, 0.0f, 1.2925156E-7f, 1.2711517E-7f, 0.0f, 3.510334f, 0.0f, 0.0f, 5.4557006E-7f, 0.0f, 1.4784654f, 1.454028f, 0.03219004f, -0.43874544f, -0.8519819f, 1.1455716f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//196996	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//196997	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//196998	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//196999	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197000	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197001	
GLES20.glUseProgram(mProgram2)        ;//197002	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197003	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197004	
GLES20.glUniform1i(mSampler,0)  ;//197005	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197006	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197007	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197008	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197009	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197010	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197011	
GLES20.glUniform1f(muAlpha, 0.5904934f);//197012	
GLES20.glUniform1f(muInfluence,0.0f);//197013	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197014	
{float value[]={-2.3794694f, 0.0f, 4.9282185E-8f, 4.8467605E-8f, 0.0f, 1.3384515f, 0.0f, 0.0f, 2.0801981E-7f, 0.0f, 0.56372255f, 0.5544048f, 1.4062479f, -0.5463625f, -0.92878354f, 1.0700395f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//197015	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197016	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197017	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197018	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197019	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197020	
GLES20.glUseProgram(mProgram2)        ;//197021	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197022	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197023	
GLES20.glUniform1i(mSampler,0)  ;//197024	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197025	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197026	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197027	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197028	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197029	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197030	
GLES20.glUniform1f(muAlpha, 0.20579901f)    ;//197031	
GLES20.glUniform1f(muInfluence,0.0f);//197032	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197033	
{float value[]={-4.3292947f, 0.0f, 8.966584E-8f, 8.818376E-8f, 0.0f, 2.4352283f, 0.0f, 0.0f, 3.7847897E-7f, 0.0f, 1.0256577f, 1.0087047f, -0.87205166f, -0.64354575f, -0.98906875f, 1.0107507f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//197034	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197035	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197036	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197037	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197038	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197039	
GLES20.glUseProgram(mProgram2)        ;//197040	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197041	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197042	
GLES20.glUniform1i(mSampler,0)  ;//197043	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197044	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197045	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197046	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197047	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197048	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197049	
GLES20.glUniform1f(muAlpha, 0.20219225f)    ;//197050	
GLES20.glUniform1f(muInfluence,0.0f);//197051	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197052	
{float value[]={-4.345956f, 0.0f, 9.0010914E-8f, 8.852313E-8f, 0.0f, 2.4446f, 0.0f, 0.0f, 3.7993553E-7f, 0.0f, 1.0296049f, 1.0125866f, -1.8783154f, -0.23692346f, -0.8405692f, 1.1567957f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//197053	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197054	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197055	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197056	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197057	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197058	
GLES20.glUseProgram(mProgram2)        ;//197059	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197060	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197061	
GLES20.glUniform1i(mSampler,0)  ;//197062	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197063	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197064	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197065	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197066	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197067	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197068	
GLES20.glUniform1f(muAlpha, 0.35541344f)    ;//197069	
GLES20.glUniform1f(muInfluence,0.0f);//197070	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197071	
{float value[]={-4.82763f, 0.0f, 9.9987076E-8f, 9.83344E-8f, 0.0f, 2.715542f, 0.0f, 0.0f, 4.2204485E-7f, 0.0f, 1.1437188f, 1.1248144f, 0.6175144f, -0.41088927f, -0.6968707f, 1.2981191f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)    ;//197072	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197073	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197074	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197075	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197076	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197077	
GLES20.glUseProgram(mProgram2)        ;//197078	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197079	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197080	
GLES20.glUniform1i(mSampler,0)  ;//197081	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197082	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197083	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197084	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197085	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197086	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197087	
GLES20.glUniform1f(muAlpha, 0.39963108f)    ;//197088	
GLES20.glUniform1f(muInfluence,0.0f);//197089	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197090	
{float value[]={-3.844531f, 0.0f, 7.962569E-8f, 7.830957E-8f, 0.0f, 2.1625488f, 0.0f, 0.0f, 3.360996E-7f, 0.0f, 0.91081184f, 0.8957571f, 0.5027408f, 0.062461987f, -0.59946764f, 1.3939121f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//197091	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197092	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197093	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197094	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197095	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197096	
GLES20.glUseProgram(mProgram2)        ;//197097	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197098	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197099	
GLES20.glUniform1i(mSampler,0)  ;//197100	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197101	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197102	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197103	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197104	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197105	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197106	
GLES20.glUniform1f(muAlpha, 0.3878631f);//197107	
GLES20.glUniform1f(muInfluence,0.0f);//197108	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197109	
{float value[]={-2.1673234f, 0.0f, 4.4888342E-8f, 4.4146386E-8f, 0.0f, 1.2191193f, 0.0f, 0.0f, 1.8947341E-7f, 0.0f, 0.5134628f, 0.5049758f, 1.0662752f, -0.42544496f, -0.7224076f, 1.2730042f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//197110	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197111	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197112	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197113	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197114	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197115	
GLES20.glUseProgram(mProgram2)        ;//197116	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197117	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197118	
GLES20.glUniform1i(mSampler,0)  ;//197119	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197120	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197121	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197122	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197123	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197124	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197125	
GLES20.glUniform1f(muAlpha, 0.34496546f)    ;//197126	
GLES20.glUniform1f(muInfluence,0.0f);//197127	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197128	
{float value[]={-5.3415356f, 0.0f, 1.1063079E-7f, 1.0880218E-7f, 0.0f, 3.0046139f, 0.0f, 0.0f, 4.669719E-7f, 0.0f, 1.2654687f, 1.2445519f, 2.4112868f, -0.05685222f, -0.3747844f, 1.6148815f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0);//197129	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197130	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197131	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197132	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197133	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197134	
GLES20.glUseProgram(mProgram2)        ;//197135	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197136	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197137	
GLES20.glUniform1i(mSampler,0)  ;//197138	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197139	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197140	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197141	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197142	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197143	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197144	
GLES20.glUniform1f(muAlpha, 0.049056087f)   ;//197145	
GLES20.glUniform1f(muInfluence,0.0f);//197146	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197147	
{float value[]={-2.8186655f, 0.0f, 5.8378564E-8f, 5.741363E-8f, 0.0f, 1.5854993f, 0.0f, 0.0f, 2.4641557E-7f, 0.0f, 0.66777295f, 0.65673536f, 0.7816399f, 0.16316691f, -0.4110006f, 1.5792639f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//197148	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197149	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197150	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197151	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197152	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197153	
GLES20.glUseProgram(mProgram2)        ;//197154	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197155	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197156	
GLES20.glUniform1i(mSampler,0)  ;//197157	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197158	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197159	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197160	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197161	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197162	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197163	
GLES20.glUniform1f(muAlpha, 0.12341197f)    ;//197164	
GLES20.glUniform1f(muInfluence,0.0f);//197165	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197166	
{float value[]={-5.8827252f, 0.0f, 1.2183959E-7f, 1.1982571E-7f, 0.0f, 3.309033f, 0.0f, 0.0f, 5.142842E-7f, 0.0f, 1.3936825f, 1.3706464f, -0.61859494f, -0.19603947f, -0.7306398f, 1.2649081f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//197167	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197168	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197169	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197170	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197171	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197172	
GLES20.glUseProgram(mProgram2)        ;//197173	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197174	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197175	
GLES20.glUniform1i(mSampler,0)  ;//197176	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197177	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197178	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197179	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197180	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197181	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197182	
GLES20.glUniform1f(muAlpha, 0.2426543f);//197183	
GLES20.glUniform1f(muInfluence,0.0f);//197184	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197185	
{float value[]={-5.0606527f, 0.0f, 1.0481331E-7f, 1.0308086E-7f, 0.0f, 2.8466172f, 0.0f, 0.0f, 4.4241634E-7f, 0.0f, 1.1989244f, 1.1791075f, -0.209544f, -0.2999919f, -0.38427818f, 1.6055448f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//197186	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197187	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197188	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197189	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197190	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197191	
GLES20.glUseProgram(mProgram2)        ;//197192	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197193	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197194	
GLES20.glUniform1i(mSampler,0)  ;//197195	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197196	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197197	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197198	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197199	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197200	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197201	
GLES20.glUniform1f(muAlpha, 0.05729884f)    ;//197202	
GLES20.glUniform1f(muInfluence,0.0f);//197203	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197204	
{float value[]={-5.243315f, 0.0f, 1.08596495E-7f, 1.06801515E-7f, 0.0f, 2.9493647f, 0.0f, 0.0f, 4.5838516E-7f, 0.0f, 1.2421992f, 1.2216669f, -1.1140529f, -0.6595374f, -0.7284234f, 1.2670878f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//197205	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197206	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197207	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197208	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197209	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197210	
GLES20.glUseProgram(mProgram2)        ;//197211	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197212	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197213	
GLES20.glUniform1i(mSampler,0)  ;//197214	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197215	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197216	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197217	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197218	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197219	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197220	
GLES20.glUniform1f(muAlpha, 0.28933862f)    ;//197221
GLES20.glUniform1f(muInfluence,0.0f);//197222	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197223	
{float value[]={-4.0317907f, 0.0f, 8.350411E-8f, 8.2123876E-8f, 0.0f, 2.2678823f, 0.0f, 0.0f, 3.5247032E-7f, 0.0f, 0.9551757f, 0.9393877f, 0.44996905f, -0.32271457f, -0.71922755f, 1.2761316f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//197224	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197225	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197226	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197227	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197228	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197229	
GLES20.glUseProgram(mProgram2)        ;//197230	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197231	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197232	
GLES20.glUniform1i(mSampler,0)  ;//197233	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197234	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197235	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197236	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197237	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197238	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197239	
GLES20.glUniform1f(muAlpha, 0.09398336f)    ;//197240	
GLES20.glUniform1f(muInfluence,0.0f);//197241	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197242	
{float value[]={-5.9476414f, 0.0f, 1.231841E-7f, 1.2114799E-7f, 0.0f, 3.3455482f, 0.0f, 0.0f, 5.199593E-7f, 0.0f, 1.4090618f, 1.3857715f, -0.43829054f, -0.83336365f, -0.80248773f, 1.1942477f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//197243	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197244	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197245	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197246	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197247	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197248	
GLES20.glUseProgram(mProgram2)        ;//197249	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197250	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197251	
GLES20.glUniform1i(mSampler,0)  ;//197252	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197253	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197254	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197255	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197256	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197257	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197258	
GLES20.glUniform1f(muAlpha, 0.06842917f)    ;//197259	
GLES20.glUniform1f(muInfluence,0.0f);//197260	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197261	
{float value[]={-3.8971646f, 0.0f, 8.0715814E-8f, 7.938167E-8f, 0.0f, 2.1921551f, 0.0f, 0.0f, 3.4070098E-7f, 0.0f, 0.9232813f, 0.90802044f, -2.1296551f, -0.36747137f, -0.7406287f, 1.2550843f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//197262	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197263	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197264	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197265	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197266	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197267	
GLES20.glUseProgram(mProgram2)        ;//197268	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197269	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197270	
GLES20.glUniform1i(mSampler,0)  ;//197271	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197272	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197273	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197274	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197275	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197276	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197277	
GLES20.glUniform1f(muAlpha, 0.17209603f)    ;//197278	
GLES20.glUniform1f(muInfluence,0.0f);//197279	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197280	
{float value[]={-2.9339974f, 0.0f, 6.076725E-8f, 5.976283E-8f, 0.0f, 1.6503736f, 0.0f, 0.0f, 2.564982E-7f, 0.0f, 0.6950964f, 0.68360716f, -0.17810394f, -0.4910022f, -0.45885748f, 1.5321982f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)         ;//197281	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197282	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197283	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197284	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197285	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197286	
GLES20.glUseProgram(mProgram2)        ;//197287	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197288	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197289	
GLES20.glUniform1i(mSampler,0)  ;//197290	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197291	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197292	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197293	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197294	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197295	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197296	
GLES20.glUniform1f(muAlpha, 0.2934833f);//197297	
GLES20.glUniform1f(muInfluence,0.0f);//197298	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197299	
{float value[]={-5.510035f, 0.0f, 1.1412065E-7f, 1.1223436E-7f, 0.0f, 3.0993948f, 0.0f, 0.0f, 4.8170256E-7f, 0.0f, 1.3053881f, 1.2838115f, -2.0283718f, -0.61192185f, -0.92898285f, 1.0698434f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)        ;//197300	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197301	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197302	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197303	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197304	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197305	
GLES20.glUseProgram(mProgram2)        ;//197306	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197307	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197308	
GLES20.glUniform1i(mSampler,0)  ;//197309	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197310	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197311	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197312	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197313	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197314	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197315	
GLES20.glUniform1f(muAlpha, 0.13177204f)    ;//197316	
GLES20.glUniform1f(muInfluence,0.0f);//197317	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197318	
{float value[]={-4.978693f, 0.0f, 1.031158E-7f, 1.0141141E-7f, 0.0f, 2.800515f, 0.0f, 0.0f, 4.3525117E-7f, 0.0f, 1.1795073f, 1.1600113f, -0.8212585f, -1.0644013f, -0.9477241f, 1.051412f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)   ;//197319	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197320	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197321	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197322	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197323	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197324	
GLES20.glUseProgram(mProgram2)        ;//197325	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197326	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197327	
GLES20.glUniform1i(mSampler,0)  ;//197328	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197329	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197330	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197331	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197332	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197333	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197334	
GLES20.glUniform1f(muAlpha, 0.18119915f)    ;//197335	
GLES20.glUniform1f(muInfluence,0.0f);//197336	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197337	
{float value[]={-5.191355f, 0.0f, 1.07520336E-7f, 1.0574314E-7f, 0.0f, 2.9201372f, 0.0f, 0.0f, 4.538427E-7f, 0.0f, 1.2298893f, 1.2095605f, 1.1151627f, -1.1005746f, -0.9689056f, 1.0305805f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0) ;//197338	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197339	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197340	
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)   ;//197341	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)      ;//197342	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197343	
GLES20.glUseProgram(mProgram2)        ;//197344	
GLES20.glActiveTexture(GLES20.GL_TEXTURE0) ;//197345	
GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])      ;//197346	
GLES20.glUniform1i(mSampler,0)  ;//197347	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])      ;//197348	
GLES20.glEnableVertexAttribArray(mTexCoordHandle) ;//197349	
GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0) ;//197350	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])      ;//197351	
GLES20.glEnableVertexAttribArray(mVertCoordHandle) ;//197352	
GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0) ;//197353	
GLES20.glUniform1f(muAlpha, 0.24587427f)    ;//197354	
GLES20.glUniform1f(muInfluence,0.0f);//197355	
GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)      ;//197356	
{float value[]={-2.3857305f, 0.0f, 4.9411867E-8f, 4.859514E-8f, 0.0f, 1.3419734f, 0.0f, 0.0f, 2.0856719E-7f, 0.0f, 0.5652059f, 0.5558636f, 0.098813176f, -0.91127354f, -0.74540377f, 1.2503881f};
GLES20.glUniformMatrix4fv(muMVPMatrix, 1, false, value, 0)       ;//197357	
}
GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])   ;//197358	
GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0)      ;//197359	

        
        
        







        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)      ; // 227429
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)              ; // 227430
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)              ; // 227431
        GLES20.glDisable(GLES20.GL_BLEND)                                     ; // 227432
        GLES20.glDisable(GLES20.GL_CULL_FACE)                                 ; // 227433
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)                                 ; // 227434
        GLES20.glDepthFunc(GLES20.GL_LESS)                                   ; // 227435
        GLES20.glDepthMask(false)                                  ; // 227436
    }


    public void loadTexture(Context context) {
        InputStream imageStream = context.getResources().openRawResource(  R.drawable.snow );

        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeStream(imageStream);
        } catch (Exception e) {

        } finally {
            try {
                imageStream.close();
                imageStream = null;
            } catch (Exception e) {

            }
        }

        GLES20.glGenTextures(1, textures, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;                       " +
                    "attribute vec2 aTextureCoord;                  " +
                    "attribute vec4 aVertexCoord;                   " +
                    "varying vec2 vTextureCoord;                    " +
                    "                                               " +
                    "void main() {                                  " +
                    "    gl_Position = uMVPMatrix * aVertexCoord;   " +
                    "    vTextureCoord = aTextureCoord;             " +
                    "}";

    private final String fragmentShaderCode = "" +
            "precision highp float;                                                              " +
            "const float ONE = 1.0;                                                              " +
            "uniform float uAlpha;                                                               " +
            "uniform float uInfluence;                                                           " +
            "uniform sampler2D uTexture;                                                         " +
            "varying vec2 vTextureCoord;                                                         " +
            "void main() {                                                                       " +
            "    vec4 color = texture2D(uTexture, vTextureCoord);                                " +
            "    gl_FragColor = vec4(color.rgb, (color.a + uInfluence*(ONE - color.a))*uAlpha);  " +
//staotemp            "    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);  " +
            "}";




    private final int mProgram2;
    private int[] textures = new int[1];

    int   mSampler;
    int   muAlpha;
    int   muInfluence;
    int   muMVPMatrix;

    private int mTexCoordHandle;
    private int mVertCoordHandle;

    private int[] mTexCoordBuf = new int[1];
    private int[] mVertCoordBuf = new int[1];
    private int[] mIndexBuf = new int[1];



    // texture coordinates
    private float texCoords[] = {        1,      1,        1,     0,        0,      1,        0,     0 };
    // 2 floats
    static int texStride = 2 * 4;
    private final FloatBuffer textureBuffer;

    // vert coord
///*  staotemp
    private float vertCoords[] = {
            -0.01f,     -0.01f,     0,
            -0.01f,     0.01f,     0,
            0.01f,     -0.01f,     0,
            0.01f,     0.01f,     0 };
//*/
/*
    private float vertCoords[] = {
            -1f,     -1,     0,
            -1,     1f,     0,
            1,     -1,     0,
            1,     1f,     0 };
*/
    private final FloatBuffer vertBuffer;

    // vert indices
    private int vertIndex[] = {
            2, 0, 3, 3, 0, 1
    };
    private final IntBuffer indexBuffer;




}

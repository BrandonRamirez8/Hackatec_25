package com.example.xalli;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private Button buttonSend;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    private GenerativeModelFutures model;
    private Executor executor = Executors.newSingleThreadExecutor();

    private static final String API_KEY = "AIzaSyDfJb_vtTkSWMEd091J-d4mYPvxbna3Swo"; // ¡ASEGÚRATE DE QUE ESTA ES TU CLAVE DE API VÁLIDA!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        recyclerViewChat = findViewById(R.id.recycler_view_chat);
        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.button_send);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(messageAdapter);

        // Inicializa el modelo de Gemini
        GenerativeModel gm = new GenerativeModel("gemini-2.5-flash", API_KEY); //
        model = GenerativeModelFutures.from(gm);

        buttonSend.setOnClickListener(v -> {
            String userMessage = editTextMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                addMessage(userMessage, true);
                editTextMessage.setText("");
                sendToGemini(userMessage);
            }
        });
    }

    private void addMessage(String text, boolean isUser) {
        messageList.add(new Message(text, isUser));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        recyclerViewChat.scrollToPosition(messageList.size() - 1);
    }

    private void sendToGemini(String query) {
        Content content = new Content.Builder().addText(query).build();
        ListenableFuture<GenerateContentResponse> result = model.generateContent(content);

        result.addListener(() -> {
            try {
                GenerateContentResponse response = result.get();
                String botResponse = response.getText();
                runOnUiThread(() -> {
                    if (botResponse != null && !botResponse.isEmpty()) {
                        addMessage(botResponse, false);
                    } else {
                        addMessage("No pude obtener una respuesta del bot.", false);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    addMessage("Error: " + e.getMessage(), false);
                    Toast.makeText(ChatbotActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            }
        }, executor);
    }
}

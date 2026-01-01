package com.automation.utils;

import com.automation.config.ConfigManager;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Slack Notifier - Sends test results to Slack
 */
public class SlackNotifier {

    private static final Logger log = LoggerFactory.getLogger(SlackNotifier.class);

    private SlackNotifier() {
        // Private constructor
    }

    public static void sendNotification(String message) {
        String webhookUrl = ConfigManager.getInstance().getSlackWebhookUrl();

        if (webhookUrl == null || webhookUrl.isEmpty()) {
            log.warn("Slack webhook URL not configured");
            return;
        }

        try {
            Slack slack = Slack.getInstance();
            Payload payload = Payload.builder()
                    .text(message)
                    .build();

            WebhookResponse response = slack.send(webhookUrl, payload);

            if (response.getCode() == 200) {
                log.info("Slack notification sent successfully");
            } else {
                log.error("Failed to send Slack notification. Response: {}", response.getBody());
            }
        } catch (Exception e) {
            log.error("Error sending Slack notification: {}", e.getMessage());
        }
    }

    public static void sendFormattedNotification(String title, String status, int passed, int failed, int skipped) {
        String message = String.format(
                "*%s*\n" +
                "Status: %s\n" +
                "━━━━━━━━━━━━━━━━━━━━\n" +
                "✅ Passed: %d\n" +
                "❌ Failed: %d\n" +
                "⏭ Skipped: %d\n" +
                "━━━━━━━━━━━━━━━━━━━━\n" +
                "📊 Pass Rate: %.2f%%",
                title, status, passed, failed, skipped,
                (passed * 100.0) / (passed + failed + skipped)
        );
        sendNotification(message);
    }
}

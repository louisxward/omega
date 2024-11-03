package org.com.model;

import java.util.List;

public record AuthResponse(boolean created, List<String> messages) {}
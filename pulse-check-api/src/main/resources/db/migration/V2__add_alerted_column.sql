-- Add alerted column to monitors table
-- This column tracks whether a monitor has already been alerted to prevent duplicate alerts
ALTER TABLE monitors ADD COLUMN alerted BOOLEAN NOT NULL DEFAULT FALSE;

-- Add index for better performance on alerted status
CREATE INDEX idx_monitors_alerted ON monitors(alerted);

import { useState, useEffect } from "react";
import usePlacesAutocomplete, { getGeocode } from "use-places-autocomplete";
import { AutoComplete, message } from "antd";
import styles from "./PlacesAutocomplete.module.css";

export const PlacesAutocomplete = ({
  onSelectLocation,
  initialValue,
  cancelChanges,
  onChange,
  style
}) => {
  const {
    ready,
    value,
    suggestions: { data },
    setValue,
    clearSuggestions,
  } = usePlacesAutocomplete({
    requestOptions: {
      types: ["(cities)"],
      fields: ["address_components", "geometry", "icon", "name"],
    },
    debounce: 300,
  });

  const handleInput = (value) => {
    setValue(value);
  };

  useEffect(() => {
    handleInput(initialValue);
  }, [cancelChanges]);

  const handleSelect = (value) => {
    setValue(value);
    clearSuggestions();
    onSelectLocation(value);
  };

  const options = data.map((suggestion) => ({
    value: suggestion.description,
    label: (
      <div>
        <strong className={styles.MainSuggestion}>
          {suggestion.structured_formatting.main_text}
        </strong>
        <small className={styles.SecondarySuggestion}>
          {suggestion.structured_formatting.secondary_text}
        </small>
      </div>
    ),
  }));

  return (
    <AutoComplete
      options={options}
      onSelect={handleSelect}
      onSearch={handleInput}
      onChange={(newValue) => {
        handleInput(newValue);
        if (onChange) onChange(newValue);
        onSelectLocation(newValue);
      }}
      value={value}
      disabled={!ready}
      placeholder="Where do you live?"
      className={styles.Select}
      style={style}
      defaultActiveFirstOption={false}
    />
  );
};
